package com.example.card_user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDto {
    private Integer imageId;
    private String imageName;
    private String path;
    private String ext;
    private byte []data;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
