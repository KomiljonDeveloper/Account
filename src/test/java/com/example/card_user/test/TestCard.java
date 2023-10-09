package com.example.card_user.test;

import com.example.card_user.dto.CardDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.dto.UserDto;
import com.example.card_user.model.Card;
import com.example.card_user.model.User;
import com.example.card_user.repository.CardRepository;
import com.example.card_user.repository.UserRepository;
import com.example.card_user.service.CardService;
import com.example.card_user.service.mapper.CardMapper;
import com.example.card_user.service.validation.CardValidation;
import com.example.card_user.utils.CardRepositoryImpl;
import jakarta.persistence.Enumerated;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(value = {MockitoExtension.class})
public class TestCard {

    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardMapper cardMapper;
    @Mock
    private CardValidation cardValidation;
    @Mock
    private CardRepositoryImpl cardRepositoryImpl;
    @Test
    void testGetPositive(){
        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(Card.builder()
                                .cardId(1)
                                .cardName("Humo")
                                .cardPassword("1205")
                                .cardNumber("5698932121549683")
                        .build()));

        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.get(any());

        Assertions.assertEquals(0,cardDtoResponseDto.getCode());
        Assertions.assertTrue(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());
        Assertions.assertEquals("Ok",cardDtoResponseDto.getMessage());


        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());


    }
    @Test
    void testGetNegative(){
        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());


        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.get(any());

        Assertions.assertEquals(-1,cardDtoResponseDto.getCode());
        Assertions.assertFalse(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());



        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());


    }


    @Test
    void testCreatePositive(){
        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.of(User.builder()
                                .id(1)
                                .email("komiljonbakhromov@gmail.com")
                                .lastName("Bakhromov")
                                .password("1234")
                        .build()));


        when(this.cardValidation.validation(any()))
                .thenReturn(new ArrayList<>());

        when(this.cardRepository.existsByCardNumber(any()))
                .thenReturn(false);

        when(this.cardMapper.toEntity(any()))
                .thenReturn(Card.builder()
                        .cardId(1)
                        .cardName("Humo")
                        .cardPassword("1205")
                        .cardNumber("5698932121549683")
                        .build());


        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.create(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build());

        Assertions.assertEquals(0,cardDtoResponseDto.getCode());
        Assertions.assertTrue(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());
        Assertions.assertEquals("Created Card!!!",cardDtoResponseDto.getMessage());


        verify(this.userRepository,times(1)).findByUserId(any());
        verify(this.cardValidation,times(1)).validation(any());
        verify(this.cardRepository,times(1)).existsByCardNumber(any());
        verify(this.cardMapper,times(1)).toEntity(any());

    }

    @Test
    void testCreateNegative(){

        when(this.userRepository.findByUserId(any()))
                .thenReturn(Optional.empty());


        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.create(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build());

        Assertions.assertEquals(-1,cardDtoResponseDto.getCode());
        Assertions.assertFalse(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());


        verify(this.userRepository,times(1)).findByUserId(any());


    }
    @Test
    void testCreateException(){

        when(this.userRepository.findByUserId(any()))
                .thenThrow(RuntimeException.class);


        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.create(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build());

        Assertions.assertEquals(-2,cardDtoResponseDto.getCode());
        Assertions.assertFalse(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());


        verify(this.userRepository,times(1)).findByUserId(any());


    }
    @Test
    void testUpdatePositive(){
         when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                 .thenReturn(Optional.ofNullable(Card.builder()
                         .cardId(1)
                         .cardName("Humo")
                         .cardPassword("1205")
                         .cardNumber("5698932121549683")
                         .build()));


        when(this.cardValidation.validation(any()))
                .thenReturn(new ArrayList<>());

        when(this.cardRepository.findByCardNumberAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(Card.builder()
                        .cardId(2)
                        .cardName("Humo")
                        .cardPassword("1205")
                        .cardNumber("5698932121549683")
                        .build()));

        when(this.cardMapper.update(any(),any()))
                .thenReturn(Card.builder()
                        .cardId(1)
                        .cardName("Humo")
                        .cardPassword("1205")
                        .cardNumber("5698932121549683")
                        .build());


        ResponseDto<CardDto> update = this.cardService.update(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build(), 2);

        Assertions.assertEquals(0,update.getCode());
        Assertions.assertTrue(update.isSuccess());
        Assertions.assertNull(update.getErrors());




        verify(this.cardValidation,times(1)).validation(any());
        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());
        verify(this.cardRepository,times(1)).findByCardNumberAndDeletedAtIsNull(any());
        verify(this.cardMapper,times(1)).update(any(),any());


    }

    @Test
    void testUpdateNegative(){

        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());


        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.update(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build(), 2);

        Assertions.assertEquals(-1,cardDtoResponseDto.getCode());
        Assertions.assertFalse(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());



        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());





    }


    @Test
    void testUpdateException(){


        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);



        ResponseDto<CardDto> cardDtoResponseDto = this.cardService.update(CardDto.builder()
                .cardId(1)
                .cardName("Humo")
                .cardPassword("1205")
                .cardNumber("5698932121549683")
                .build(), 2);

        Assertions.assertEquals(-2,cardDtoResponseDto.getCode());
        Assertions.assertFalse(cardDtoResponseDto.isSuccess());
        Assertions.assertNull(cardDtoResponseDto.getErrors());



        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());

    }

    @Test
    void testDeletePositive(){

        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(Card.builder()
                        .cardId(1)
                        .cardName("Humo")
                        .cardPassword("1205")
                        .cardNumber("5698932121549683")
                        .build()));

        ResponseDto<CardDto> delete = this.cardService.delete(any());

        Assertions.assertEquals(0,delete.getCode());
        Assertions.assertTrue(delete.isSuccess());
        Assertions.assertNull(delete.getErrors());





        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());


    }
    @Test
    void testDeleteNegative(){
        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CardDto> delete = this.cardService.delete(any());

        Assertions.assertEquals(-1,delete.getCode());
        Assertions.assertFalse(delete.isSuccess());
        Assertions.assertNull(delete.getErrors());
        Assertions.assertEquals("Card not found!",delete.getMessage());


        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());


    }
    @Test
    void testDeleteException(){

        when(this.cardRepository.findByCardIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<CardDto> delete = this.cardService.delete(any());

        Assertions.assertEquals(-2,delete.getCode());
        Assertions.assertFalse(delete.isSuccess());
        Assertions.assertNull(delete.getErrors());


        verify(this.cardRepository,times(1)).findByCardIdAndDeletedAtIsNull(any());





    }
}
