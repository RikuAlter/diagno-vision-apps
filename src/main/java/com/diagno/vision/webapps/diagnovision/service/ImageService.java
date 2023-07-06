package com.diagno.vision.webapps.diagnovision.service;

import com.diagno.vision.webapps.diagnovision.dto.ImageData;
import com.diagno.vision.webapps.diagnovision.dto.User;
import com.diagno.vision.webapps.diagnovision.repository.ImageRepository;
import com.diagno.vision.webapps.diagnovision.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class ImageService {

    @Autowired
    private ImageKafkaProducer imageKafkaProducer;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    public boolean uploadFile(final MultipartFile file, final Date createdAt){
        final String methodName = "ImageService.uploadImage";
        boolean isUploadSuccessful = false;
        try {
            byte[] imageData = file.getBytes();

            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null && auth.isAuthenticated()) {
                final User savedUser = userRepository.findByEmail(auth.getName()).orElse(null);
                final ImageData savedImage = saveImageToRepository(createdAt, savedUser);
                imageKafkaProducer.sendMessage(imageData, savedImage);
                isUploadSuccessful = true;
            } else {
                throw new BadCredentialsException("Invalid user!");
            }
        } catch (IOException e) {
            log.error("{}: Image data is invalid! {}", methodName, e.getMessage());
        } catch (KafkaException e) {
            log.error("{}: Image upload failed due to a Kafka failure! Please contact support for further assistance.", methodName);
        } catch (Exception e) {
            log.error("{}: Failed to save image metadata for user! Please contact support for further assistance.", methodName);
        }
        return isUploadSuccessful;
    }

    private ImageData saveImageToRepository(Date createdAt, User savedUser) {
        final ImageData image = ImageData.builder()
                .user(savedUser)
                .creationDate(createdAt)
                .uploadDate(new Date()).build();
        return imageRepository.save(image);
    }
}
