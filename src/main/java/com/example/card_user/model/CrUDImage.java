package com.example.card_user.model;

import com.example.card_user.dto.ImageDto;
import com.example.card_user.dto.ResponseDto;

public interface CrUDImage<T,Y,U> {
    ResponseDto<T> upload(Y image,U userId);
    ResponseDto<T> download(U imageId);
    ResponseDto<T> update(Y image,U imageId);
    ResponseDto<T> delete(U imageId);
}
