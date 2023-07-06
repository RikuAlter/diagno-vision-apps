package com.diagno.vision.webapps.diagnovision.controller;

import com.diagno.vision.webapps.diagnovision.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @RequestParam("createdAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date createdAt){
        boolean isImageUploaded = imageService.uploadFile(file, createdAt);

        if(isImageUploaded)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image uploaded successfully!");
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Image upload failed!");
    }
}
