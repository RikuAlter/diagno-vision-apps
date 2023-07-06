package com.diagno.vision.webapps.diagnovision.service;

import com.diagno.vision.webapps.diagnovision.dto.ImageData;
import com.diagno.vision.webapps.diagnovision.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageKafkaProducer {

    @Autowired
    private KafkaTemplate<Integer, byte[]> kafkaTemplate;
    @Autowired
    private ImageRepository imageRepository;
    @Value("${spring:kafka:topic-name}")
    private String topic;

    public void sendMessage(final byte[] imageData, final ImageData image) throws KafkaException{
        final Integer imageId = image.getId();
        CompletableFuture<SendResult<Integer, byte[]>> future = kafkaTemplate.send(topic, imageId, imageData);
        future.whenComplete((success, ex) -> {
            if (!Objects.nonNull(ex)) {
                image.setMarkedForDeletion(true);
                imageRepository.save(image);
                throw new KafkaException("Image upload failed");
            }
        });
    }
}
