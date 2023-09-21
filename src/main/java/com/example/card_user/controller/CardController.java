package com.example.card_user.controller;

import com.example.card_user.dto.CardDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.dto.UserDto;
import com.example.card_user.model.CrUDSimple;
import com.example.card_user.service.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodType;
import java.util.Map;

@RestController
@RequestMapping("card")
public class CardController implements CrUDSimple<CardDto,Integer> {

    @Autowired
    private CardService cardService;

    @Override
    @Operation(
            tags = {"Salom","Helllo"},
            method = "Post",
            summary = "This method is entries users information!",
            description = "This is method description!"
    )
    @PostMapping("/create")
    public ResponseDto<CardDto> create(@RequestBody @Valid CardDto dto) {
        return this.cardService.create(dto);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseDto<CardDto> delete(@RequestParam(value = "id") Integer id) {
        return this.cardService.delete(id);
    }

    @Override
    @PutMapping("/update")
    public ResponseDto<CardDto> update(@RequestBody CardDto dto, @RequestParam(value = "id") Integer id) {
        return this.cardService.update(dto,id);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseDto<CardDto> get(@PathVariable Integer id) {
        return this.cardService.get(id);
    }

    @GetMapping("/search-by-basic")
    public ResponseDto<Page<CardDto>> searchByBasic(@RequestParam Map<String,String> params){
        return this.cardService.searchByBasic(params);
    }

    @GetMapping("/search-by-advanced")
    public ResponseDto<Page<CardDto>> searchByAdvanced(@RequestParam Map<String,String> params){
        return this.cardService.searchByAdvanced(params);
    }

}
