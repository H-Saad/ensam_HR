package com.hr.springboot.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hr.springboot.userData_module.services.UserService;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService us;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        
        if(userDetails==null) {
        	throw new BadCredentialsException("Incorrect username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        	if (!userDetails.isEnabled()) {
                throw new DisabledException("Your account is disabled contact RH Admin");
            }
            
            if(!userDetails.isAccountNonLocked()) {
            	throw new LockedException("Your account is locked contact RH Admin");
            }
        	us.increaseFailedAttempt(username);
            throw new BadCredentialsException("Incorrect username or password");
        }

        if (!userDetails.isEnabled()) {
            throw new DisabledException("Your account is disabled contact RH Admin");
        }
        
        if(!userDetails.isAccountNonLocked()) {
        	throw new LockedException("Your account is locked contact RH Admin");
        }
        us.resetAttempts(username);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}