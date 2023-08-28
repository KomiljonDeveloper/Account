package com.example.card_user.service;

import com.example.card_user.dto.ErrorDto;
import com.example.card_user.dto.ImageDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.dto.UserDto;
import com.example.card_user.model.Image;
import com.example.card_user.repository.ImageRepository;
import com.example.card_user.repository.UserRepository;
import com.example.card_user.service.mapper.ImageMapper;
import com.example.card_user.service.mapper.UserMapper;
import com.example.card_user.model.CrUDSimple;
import com.example.card_user.model.User;
import com.example.card_user.service.validation.UserValidation;
import com.example.card_user.utils.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements CrUDSimple<UserDto, Integer> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CardService cardService;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final UserValidation userValidation;
    private final UserRepositoryImpl userRepositoryImpl;


    @Override
    public ResponseDto<UserDto> create(UserDto dto) {
        dto.setCreatedAt(LocalDateTime.now());

        List<ErrorDto> errors = userValidation.validation(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .message("Validation Error")
                    .code(-4)
                    .errors(errors)
                    .build();
        }

        if (this.userRepository.existsByEmailAndDeletedAtIsNull(dto.getEmail())) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .message("This email already exists!")
                    .build();
        }
        userRepository.save(userMapper.toEntity(dto));
        return ResponseDto.<UserDto>builder()
                .success(true)
                .message("Ok")
                .date(dto)
                .build();
    }

    @Override
    public ResponseDto<UserDto> delete(Integer id) {
        try {
            return this.userRepository.findByUserId(id).map(user1 -> {
                user1.setDeletedAt(LocalDateTime.now());
                this.userRepository.save(user1);
                return ResponseDto.<UserDto>builder()
                        .success(true)
                        .message("Deleted User!!!")
                        .build();
            }).orElse(ResponseDto.<UserDto>builder()
                    .message("User not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }


    }

    @Override
    public ResponseDto<UserDto> update(UserDto dto, Integer id) {
        try {
            return this.userRepository.findByUserId(id).map(user1 -> {
                List<ErrorDto> errors = userValidation.validation(dto);
                if (!errors.isEmpty()) {
                    return ResponseDto.<UserDto>builder()
                            .message("Validation Error")
                            .code(-4)
                            .errors(errors)
                            .build();
                }

                if (dto.getEmail() != null && !this.userRepository.findByEmailAndDeletedAtIsNull(dto.getEmail()).get().getId().equals(id)) {
                    return ResponseDto.<UserDto>builder()
                            .message("This email already exists!")
                            .code(-3)
                            .build();
                }
                user1.setUpdatedAt(LocalDateTime.now());
                this.userMapper.update(user1, dto);
                this.userRepository.save(user1);
                return ResponseDto.<UserDto>builder()
                        .success(true)
                        .message("Update User!!!")
                        .date(this.userMapper.toDto(user1))
                        .build();
            }).orElse(ResponseDto.<UserDto>builder()
                    .message("User not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> get(Integer id) {
        try {
            return this.userRepository.findByUserId(id).map(user1 -> {
                Optional<Image> optional = this.imageRepository.findByUserIdAndDeletedAtIsNull(user1.getId());

                try {
                    if (optional.isPresent()) {
                        ImageDto dto1 = this.imageMapper.toDto(optional.get());
                        dto1.setData(Files.readAllBytes(Path.of(dto1.getPath())));
                        UserDto dto = this.userMapper.toDto(user1);
                        dto.setImage(dto1);
                        dto.setCards(cardService.cardListFromDto(user1.getId()));
                        return ResponseDto.<UserDto>builder()
                                .success(true)
                                .message("OK")
                                .date(dto)
                                .build();
                    } else {
                        return ResponseDto.<UserDto>builder()
                                .message("Image not found!")
                                .code(-1)
                                .build();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).orElse(ResponseDto.<UserDto>builder()
                    .message("User not found!")
                    .code(-1)
                    .build());

        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(-2)
                    .message(String.format("Error text : %s", e.getMessage()))
                    .build();
        }

    }

    public ResponseDto<Page<UserDto>> getAllByPage(Integer page, Integer size) {
        Page<User> userPage = this.userRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        Page<UserDto> map = userPage.map(this.userMapper::toDto);
        if (map.isEmpty()) {
            return ResponseDto.<Page<UserDto>>builder()
                    .code(-1)
                    .message("Users not found!")
                    .build();
        }
        return ResponseDto.<Page<UserDto>>builder()
                .message("Ok")
                .success(true)
                .date(map)
                .build();
    }

    public ResponseDto<Page<UserDto>> getAllByValue(Integer page, Integer size, String value) {
        Page<UserDto> map = this.userRepository.findByPageOnOrderBy(PageRequest.of(page, size), value).map(this.userMapper::toDto);
        if (map.isEmpty()) {
            return ResponseDto.<Page<UserDto>>builder()
                    .code(-1)
                    .message("Users not found!")
                    .build();
        }
        return ResponseDto.<Page<UserDto>>builder()
                .message("OK")
                .success(true)
                .date(map)
                .build();

    }

    public ResponseDto<List<UserDto>> getAllByEmail(String email) {
        List<User> users = this.userRepository.findByEmail(email);
        if (users.isEmpty()) {
            return ResponseDto.<List<UserDto>>builder()
                    .message("Users not found!")
                    .code(-1)
                    .build();
        }

        List<UserDto> list = users.stream().map(this.userMapper::toDto).toList();
        return ResponseDto.<List<UserDto>>builder()
                .success(true)
                .message("OK")
                .date(list)
                .build();
    }

    public ResponseDto<Page<UserDto>> searchByBasic(Map<String, String> params) {
        int page = 0, size = 10;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
        }

        Page<UserDto> map = this.userRepository.searchByBasic(
                params.get("id") == null ? null : Integer.parseInt(params.get("id")),
                params.get("firstname"),
                params.get("lastname"),
                params.get("birthday"),
                params.get("email"),
                PageRequest.of(page, size)
        ).map(this.userMapper::toDto);


        if (!map.isEmpty())
            return ResponseDto.<Page<UserDto>>builder()
                    .message("OK")
                    .success(true)
                    .date(map)
                    .build();
        else
            return ResponseDto.<Page<UserDto>>builder()
                    .message("Users not found!")
                    .code(-1)
                    .build();
    }

    public ResponseDto<Page<UserDto>> searchByAdvanced(Map<String, String> params) {
        return Optional.of(this.userRepositoryImpl.searchByAdvanced(params).map(this.userMapper::toDto)).map(users ->
                ResponseDto.<Page<UserDto>>builder()
                        .success(true)
                        .message("OK")
                        .date(users)
                        .build()
        ).orElse(

                ResponseDto.<Page<UserDto>>builder()
                        .code(-1)
                        .message("User is not found!")
                        .build());
    }
}
