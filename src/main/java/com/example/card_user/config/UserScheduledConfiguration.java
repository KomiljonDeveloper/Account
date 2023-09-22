package com.example.card_user.config;

import com.example.card_user.dto.ResponseDto;
import com.example.card_user.model.User;
import com.example.card_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class UserScheduledConfiguration {

    private final UserRepository userRepository;

    //todo: fixedDelay  -> print() -- 2 -- print()
    //todo: fixedRate   -> print() -- 2 -- print()
//    @Scheduled(fixedRate = 4,initialDelay = 5 ,timeUnit = TimeUnit.SECONDS)
//    public static void print() throws InterruptedException {
//        Thread.sleep(2000);
//        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
//    }

    @Scheduled(cron = "10 * * * * *")
    private void clearDeletingInformation(){
        List<User> list = userRepository.findAllByDeletedAtIsNotNull();
        this.userRepository.deleteAll(list);
        if (list.isEmpty()) {
            ResponseDto.<User>builder()
                    .message("User are not found!")
                    .code(-1)
                    .build();
            return;
        }
        ResponseDto.<User>builder()
                .success(true)
                .message("User are deleting!")
                .build();
    }

}
