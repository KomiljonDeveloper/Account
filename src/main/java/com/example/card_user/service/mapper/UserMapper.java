package com.example.card_user.service.mapper;

import com.example.card_user.dto.UserDto;
import com.example.card_user.model.User;
import com.example.card_user.service.CardService;
import org.mapstruct.*;

@Mapper(componentModel = "spring",imports = CardService.class)
public abstract class UserMapper {
    @Mapping(target = "id",ignore = true)
     public abstract User toEntity(UserDto dto);
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    public abstract UserDto toDto(User user);


   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,resultType = User.class)
    public abstract User update(@MappingTarget User user, UserDto dto);

}
