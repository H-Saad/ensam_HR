package com.hr.springboot.mailing_module.services;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.userData_module.models.User;

@Service
public class MailService {
	final String username = "noreply-grh@ensam.um5.ac.ma";
    final String password = "whysoserious-ss147";

    public void sendmail(User u, HashMap<String,String> req) {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(req.get("objet"));
            message.setText(req.get("body"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));

            // Submit the email sending task to the executor service
            executorService.submit(() -> {
				try {
					Transport.send(message);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});

            // Shutdown the executor service when all tasks are completed
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendmail(User u, HashMap<String,String> req, String filename) {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        try {
            // Create the email body text
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(req.get("body"));

            // Create the email attachment
            MimeBodyPart filePart = new MimeBodyPart();
            File file = new File(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename);
            filePart.attachFile(file);

            // Combine the text and file parts into a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(filePart);

            // Create a single email recipient
            String recipient = u.getEmail();

            // Create a new MimeMessage object for the email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(req.get("objet"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setContent(multipart);

            // Submit the email sending task to the executor service
            executorService.submit(new SendEmailTask(message));

            // Shutdown the executor service when all tasks are completed
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMultiple(ArrayList<HashMap<String,Object>> req) {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
        
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Batch the emails and send them in groups
        for (int i = 0; i < req.size(); i += 10) {
            int start = i;
            int end = Math.min(i + 10, req.size());
            executorService.submit(() -> {
                try {
                    for (int j = start; j < end; j++) {
                        // Create a MimeMessage for each recipient
                    	User u = (User) req.get(j).get("user");
                    	String objet = (String) req.get(j).get("objet");
                    	String body = (String) req.get(j).get("body");
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
                        message.setSubject(objet);
                        message.setText(body);
                        Transport.send(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Shut down the thread pool after all emails have been sent
        executorService.shutdown();
    }
    
    static class SendEmailTask implements Runnable {
        private final MimeMessage message;

        public SendEmailTask(MimeMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}

