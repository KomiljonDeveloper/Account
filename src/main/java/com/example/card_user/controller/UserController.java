package com.example.card_user.controller;

import com.example.card_user.dto.ResponseDto;
import com.example.card_user.dto.UserDto;
import com.example.card_user.model.CrUDSimple;
import com.example.card_user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class UserController implements CrUDSimple<UserDto,Integer> {

   private final UserService userService;
    @Override
    @PostMapping("/create")
    public ResponseDto<UserDto> create(@RequestBody @Valid UserDto dto){
        return this.userService.create(dto);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseDto<UserDto> delete(@RequestParam(value ="id") Integer id) {
        return this.userService.delete(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseDto<UserDto> update(@RequestBody UserDto dto, @PathVariable Integer id) {
        return this.userService.update(dto,id);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseDto<UserDto> get(@PathVariable Integer id) {
        return this.userService.get(id);
    }
    @GetMapping("/get-all-page")
    public ResponseDto<Page<UserDto>> getAllByPage(@RequestParam Integer page,@RequestParam Integer size){
    return this.userService.getAllByPage(page,size);
    }

    @GetMapping("/get-all-page-value")
    public ResponseDto<Page<UserDto>> getAllByValue(@RequestParam Integer page,@RequestParam Integer size,@RequestParam String value){
        return this.userService.getAllByValue(page,size,value);

    }

    @GetMapping("/get-by-email")
    public ResponseDto<List<UserDto>> getAllByValue(@RequestParam String email){
        return this.userService.getAllByEmail(email);


    }

    @GetMapping("/search-by-basic")
    public ResponseDto<Page<UserDto>> searchByBasic(@RequestParam Map<String,String> params){
      return   this.userService.searchByBasic(params);


    }


}
