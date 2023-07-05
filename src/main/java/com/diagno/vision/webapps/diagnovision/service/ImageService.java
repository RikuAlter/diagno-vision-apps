package com.diagno.vision.webapps.diagnovision.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageService {

    @Autowired
    private ImageKafkaProducer imageKafkaProducer;

    public boolean uploadFile(final MultipartFile file){
        final String methodName = "ImageService.uploadImage";
        boolean isUploadSuccessful = false;
        try {
            byte[] imageData = file.getBytes();
            imageKafkaProducer.sendMessage(imageData);
        } catch (IOException e) {
            log.error("{}: Image data is invalid! {}", methodName, e.getMessage());
            return isUploadSuccessful;
        }
    }
}
