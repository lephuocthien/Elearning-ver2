/**
 * Dec 26, 2020
 * 4:27:09 PM
 * @author LeThien
 */
package com.lethien.elearning.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class ApiUploadController {
	@Autowired
	private ServletContext servletContext;

	@Value("${file.upload-dir}")
	private String uploadFolder;

	@Value("${file.upload-dir-return}")
	private String pathReturn;

	@PostMapping(value = "upload", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object upload(@RequestParam() MultipartFile file) {
		try {
			String pathName = System.getProperty("user.dir");
			pathName += uploadFolder;
			File dir = new File(pathName);
			if (!dir.exists())
				dir.mkdirs();

			String pathSource = pathName + file.getOriginalFilename();
			File serverFile = new File(pathSource);
			FileOutputStream stream;
			try {
				stream = new FileOutputStream(serverFile);
				stream.write(file.getBytes());
				stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

//			Map<String, String> result = new HashMap<String, String>();
//			result.put("url", file.getOriginalFilename());
			return new ResponseEntity<Object>(file.getOriginalFilename(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping(value = "load/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
		String pathName = System.getProperty("user.dir");
		pathName += uploadFolder+fileName;
		return new FileSystemResource(pathName);
	}
}
