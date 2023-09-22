package com.example.card_user.config;

import com.example.card_user.model.Image;
import com.example.card_user.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ImageScheduledConfiguration {
    private final ImageRepository imageRepository;
    @Scheduled(cron = "10 * * * * *")
    private void clearDeletingImage(){
        List<Image> images =
                this.imageRepository.deletingImage();

         this.imageRepository.deleteAll(images);
    }

}
