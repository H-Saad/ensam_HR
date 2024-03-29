package com.hr.springboot.service_module.controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.service_module.services.DocService;

@CrossOrigin
@RestController
@RequestMapping("file")
public class FileController {
	
	public static final String UPLOADDIR = System.getProperty("user.dir") + CONSTS.UPLOAD_DIR;
	public static final String DOWNDIR = System.getProperty("user.dir") + CONSTS.DOC_DIR;
	
	@Autowired
	private DocService ds;
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("/upload")
    public ResponseEntity<HashMap<String,String>> uploadFiles(@RequestParam("files")MultipartFile multipartFile) throws IOException {

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path fileStorage = get(DOWNDIR, filename).toAbsolutePath().normalize();
        copy(multipartFile.getInputStream(), fileStorage, REPLACE_EXISTING);
        HashMap<String,String> resp = new HashMap<String,String>();
        resp.put("filename", filename);
        return ResponseEntity.status(200).body(resp);
    }

	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
		if(filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
			return ResponseEntity.status(400).body(null);
		}
		String f = filename;
		if(filename.equals("vie communale")) {
			f = ds.genererVieCommunal();
		}
		if(filename.equals("fichier plat administratif")) {
			f = ds.genererFichierPlatAdministratif();
		}
		if(filename.equals("fichier plat enseignant")) {
			f = ds.genererFichierPlatEnseignant();
		}
        Path filePath = get(DOWNDIR).toAbsolutePath().normalize().resolve(f);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(f + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", f);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.status(200).contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }
	
}
