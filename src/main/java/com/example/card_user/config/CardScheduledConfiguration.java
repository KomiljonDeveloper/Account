package com.example.card_user.config;

import com.example.card_user.model.Card;
import com.example.card_user.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CardScheduledConfiguration {
   private final CardRepository cardRepository;
    @Scheduled(
            cron = "0 0 0 1 * *"
    )
    private void clearDeletingCard(){
        List<Card> allByDeletedAtIsNotNull =
                this.cardRepository.findAllByDeletedAtIsNotNull();
         this.cardRepository.deleteAll(allByDeletedAtIsNotNull);
    }

}
