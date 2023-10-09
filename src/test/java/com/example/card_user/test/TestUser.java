package com.example.card_user.test;

import com.example.card_user.dto.ErrorDto;
import com.example.card_user.dto.ImageDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.dto.UserDto;
import com.example.card_user.model.Image;
import com.example.card_user.model.User;
import com.example.card_user.repository.ImageRepository;
import com.example.card_user.repository.UserRepository;
import com.example.card_user.service.CardService;
import com.example.card_user.service.UserService;
import com.example.card_user.service.mapper.ImageMapper;
import com.example.card_user.service.mapper.UserMapper;
import com.example.card_user.service.validation.UserValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(value = {MockitoExtension.class})
public class TestUser {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserValidation userValidation;

    @Mock
    private CardService cardService;

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageMapper imageMapper;

    @Test
    void testGetPositive() {

        when(this.imageRepository.findByUserIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(Image.builder()
                                .imageId(1)
                                .userId(2)
                                .imageName("Komiljon")
                                .ext("doc")
                                .path("upload/22/08/2023/3f302a3f-f23e-4d4a-bd65-fcfee67a0a4c")
                        .build()));

       when(this.userRepository.findByUserId(any()))
               .thenReturn(Optional.of(User.builder()
                               .id(1)
                               .email("komiljonbakhromov@gmail.com")
                               .firstName("Komiljon")
                               .lastName("Bakhromov")
                       .build()));

        when(this.imageMapper.toDto(any()))
                .thenReturn(ImageDto.builder()
                        .imageId(1)
                        .imageName("Komiljon")
                        .ext("doc")
                        .path("upload/22/08/2023/3f302a3f-f23e-4d4a-bd65-fcfee67a0a4c")
                        .build());

        when(this.userMapper.toDto(any()))
                .thenReturn(
                        UserDto.builder()
                                .firstName("Komiljon")
                                .lastName("Bakhromov")
                                .email("komiljnbakhromov@gmail.com")
                                .build());

        ResponseDto<UserDto> responseDto = this.userService.get(any());
        Assertions.assertEquals("OK",responseDto.getMessage());
        Assertions.assertEquals(0,responseDto.getCode());
        Assertions.assertTrue(responseDto.isSuccess());
        Assertions.assertNull(responseDto.getErrors());


        verify(this.userMapper,times(1)).toDto(any());
        verify(this.imageMapper,times(1)).toDto(any());
        verify(this.userRepository,times(1)).findByUserId(any());
        verify(this.imageRepository,times(1)).findByUserIdAndDeletedAtIsNull(any());

    }

    @Test
    void testGetNegative() {


        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.empty());

        ResponseDto<UserDto> responseDto = this.userService.get(any());

        Assertions.assertEquals(-1,responseDto.getCode());
        Assertions.assertFalse(responseDto.isSuccess());
        Assertions.assertNull(responseDto.getErrors());
        Assertions.assertEquals("User not found!",responseDto.getMessage());

        verify(this.userRepository,times(1)).findByUserId(any());

    }

    @Test
    void testGetException() {

        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .email("komiljonbakhromov@gmail.com")
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .build()));

        when(this.imageRepository.findByUserIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(Image.builder()
                        .imageId(1)
                        .userId(2)
                        .imageName("Komiljon")
                        .ext("doc")
                        .build()));

        ResponseDto<UserDto> responseDto = this.userService.get(any());

        Assertions.assertEquals(-2,responseDto.getCode());
        Assertions.assertFalse(responseDto.isSuccess());
        Assertions.assertNull(responseDto.getErrors());

        verify(this.imageRepository,times(1)).findByUserIdAndDeletedAtIsNull(any());
        verify(this.userRepository,times(1)).findByUserId(any());

    }

    @Test
    void testCreatePositive() {
        when(this.userMapper.toEntity(any()))
                .thenReturn(
                        User.builder()
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljnbakhromov@gmail.com")
                        .build());

        when(this.userMapper.toDto(any()))
                .thenReturn(
                        UserDto.builder()
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljnbakhromov@gmail.com")
                        .build());

        ResponseDto<UserDto> response = this.userService.create(any());

        Assertions.assertEquals(0,response.getCode());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getDate());


        verify(this.userMapper,times(1)).toEntity(any());
        verify(this.userMapper,times(1)).toDto(any());


    }

    @Test
    void testCreateValidation() {
    when(this.userValidation.validation(any()))
            .thenReturn(List.of(new ErrorDto("userId","This column cannot be empty!")));

   when(this.userMapper.toEntity(any()))
           .thenReturn(User.builder()
                   .id(1)
                   .firstName("Komiljon")
                   .lastName("Bakhromov")
                   .email("komiljonbakhromov@gmail.com")
                   .build());

        ResponseDto<UserDto> responseDto = this.userService.create(any());

        Assertions.assertEquals(-4,responseDto.getCode());
        Assertions.assertFalse(responseDto.isSuccess());
        Assertions.assertNull(responseDto.getDate());
        Assertions.assertNotNull(responseDto.getErrors());

        verify(this.userMapper,times(1)).toEntity(any());
        verify(this.userValidation,times(1)).validation(any());

    }

    @Test
    void testDeletePositive() {
       when(this.userRepository.findByUserId(any()))
               .thenReturn(Optional.of(User.builder()
                       .id(1)
                       .firstName("Komiljon")
                       .lastName("Bakhromov")
                       .email("komiljonbakhromov@gmail.com")
                       .build()));

        ResponseDto<UserDto> delete = this.userService.delete(any());

        Assertions.assertNull(delete.getErrors());
        Assertions.assertTrue(delete.isSuccess());
        Assertions.assertEquals(0,delete.getCode());

        verify(this.userRepository,times(1)).findByUserId(any());

    }


    @Test
    void testDeleteNegative() {



        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.empty());


        ResponseDto<UserDto> delete = this.userService.delete(any());

        Assertions.assertNull(delete.getErrors());
        Assertions.assertFalse(delete.isSuccess());
        Assertions.assertEquals(-1,delete.getCode());
        Assertions.assertEquals("User not found!",delete.getMessage());

        verify(this.userRepository,times(1)).findByUserId(any());

    }

    @Test
    void testDeleteException() {
        when(this.userRepository.save(any()))
                .thenThrow(RuntimeException.class);

        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljonbakhromov@gmail.com")
                        .build()));

        ResponseDto<UserDto> delete = this.userService.delete(any());

        Assertions.assertFalse(delete.isSuccess());
        Assertions.assertEquals(-2,delete.getCode());

        verify(this.userRepository,times(1)).save(any());
        verify(this.userRepository,times(1)).findByUserId(any());
    }

    @Test
    void testUpdatePositive() {
        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljonbakhromov@gmail.com")
                        .build()));

        when(this.userValidation.validation(any()))
                .thenReturn(new ArrayList<>());


        when(this.userRepository.findByEmailAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(User.builder()
                                .id(1)
                                .firstName("Komiljon")
                                .lastName("Bakhromov")
                                .email("kom1ljonbakhromov@gmail.com")
                                .build()));

        when(this.userMapper.update(any(),any()))
                .thenReturn(User.builder()
                        .id(1)
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("kom2ljonbakhromov@gmail.com")
                        .build());

        ResponseDto<UserDto> update = this.userService.update(UserDto.builder()
                .id(3)
                .firstName("Komiljon")
                .lastName("Bakhromov")
                .email("kom3ljonbakhromov@gmail.com")
                .build(), 1);

        Assertions.assertEquals(0,update.getCode());
        Assertions.assertTrue(update.isSuccess());
        Assertions.assertNull(update.getErrors());

        verify(this.userRepository,times(1)).findByUserId(any());
        verify(this.userValidation,times(1)).validation(any());
        verify(this.userRepository,times(1)).findByEmailAndDeletedAtIsNull(any());
        verify(this.userMapper,times(1)).update(any(),any());




    }


    @Test
    void testUpdateNegative() {

        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.empty());


        ResponseDto<UserDto> update = this.userService.update(any(),1);

        Assertions.assertFalse(update.isSuccess());
        Assertions.assertNull(update.getDate());
        Assertions.assertEquals(-1,update.getCode());

        verify(this.userRepository,times(1)).findByUserId(any());





    }

    @Test
    void testUpdateException() {

        when(this.userValidation.validation(any()))
                .thenReturn(new ArrayList<>());

        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljonbakhromov@gmail.com")
                        .build()));

        ResponseDto<UserDto> update = this.userService.update(any(),1);

        Assertions.assertFalse(update.isSuccess());
        Assertions.assertNull(update.getDate());
        Assertions.assertEquals(-2,update.getCode());

        verify(this.userRepository,times(1)).findByUserId(any());
        verify(this.userValidation,times(1)).validation(any());

    }

    @Test
    void testUpdateValidation() {
        when(this.userValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("email","this")));


        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .firstName("Komiljon")
                        .lastName("Bakhromov")
                        .email("komiljonbakhromov@gmail.com")
                        .build()));

        ResponseDto<UserDto> update = this.userService.update(any(), 1);

        Assertions.assertFalse(update.isSuccess());
        Assertions.assertNull(update.getDate());
        Assertions.assertEquals(-4,update.getCode());
        Assertions.assertEquals("Validation Error",update.getMessage());

        verify(this.userRepository,times(1)).findByUserId(any());
        verify(this.userValidation,times(1)).validation(any());

    }


}
