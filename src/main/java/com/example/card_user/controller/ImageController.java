package com.example.card_user.controller;

import com.example.card_user.dto.ImageDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.model.CrUDImage;
import com.example.card_user.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController implements CrUDImage<ImageDto, MultipartFile, Integer> {

    private final ImageService imageService;

    @Override
    @PostMapping("/upload/{userId}")
    public ResponseDto<ImageDto> upload(@RequestBody MultipartFile image,@PathVariable Integer userId) {
        return this.imageService.upload(image,userId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseDto<ImageDto> download(@PathVariable Integer fileId){
        return this.imageService.download(fileId);
    }

    @PutMapping("/update/{fileId}")
    public ResponseDto<ImageDto> update(@RequestBody MultipartFile file,@PathVariable Integer fileId){
        return this.imageService.update(file,fileId);
    }

    @DeleteMapping("/delete")
    public ResponseDto<ImageDto> delete(@RequestParam(name = "fileId") Integer fileId){
        return this.imageService.delete(fileId);
    }
}
