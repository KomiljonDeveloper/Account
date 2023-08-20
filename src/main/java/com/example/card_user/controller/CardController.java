package com.example.card_user.controller;

import com.example.card_user.dto.CardDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.model.CrUDSimple;
import com.example.card_user.service.CardService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController implements CrUDSimple<CardDto,Integer> {

    @Autowired
    private CardService cardService;

    @Override
    @PostMapping("/create")
    public ResponseDto<CardDto> create(@RequestBody CardDto dto) {
        return this.cardService.create(dto);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseDto<CardDto> delete(@RequestParam(value = "id") Integer id) {
        return this.cardService.delete(id);
    }

    @Override
    @PutMapping("/update")
    public ResponseDto<CardDto> update(@RequestBody @Valid CardDto dto, @RequestParam(value = "id") Integer id) {
        return this.cardService.update(dto,id);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseDto<CardDto> get(@PathVariable Integer id) {
        return this.cardService.get(id);
    }
}
