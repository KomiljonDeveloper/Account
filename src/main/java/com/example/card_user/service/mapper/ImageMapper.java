package com.example.card_user.service.mapper;

import ch.qos.logback.core.model.ModelConstants;
import com.example.card_user.dto.ImageDto;
import com.example.card_user.model.Image;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target = "user",ignore = true)
    public abstract ImageDto toDto(Image image);
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    public abstract ImageDto toDtoWithUser(Image image);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void UpdateToDtoFromEntity(@MappingTarget Image image, ImageDto dto);

}
