package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "updatedAt", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = this.userService.findAll(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable UUID userId) {
        var userModel = this.userService.findById(userId);

        if (userModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(userModel.get());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
            @RequestBody @Validated(UserDto.View.UserPut.class) @JsonView(UserDto.View.UserPut.class) UserDto userDto) {
        var userModelOptional = this.userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userModel = userModelOptional.get();
        userModel.setFullName(userDto.getFullName());
        userModel.setPhoneNumber(userDto.getPhoneNumber());
        userModel.setCpf(userDto.getCpf());
        userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        this.userService.save(userModel);

        return ResponseEntity.ok().body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable UUID userId,
            @RequestBody @Validated(UserDto.View.PasswordPut.class) @JsonView(UserDto.View.PasswordPut.class) UserDto userDto) {
        var userModelOptional = this.userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!userModelOptional.get().getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        var userModel = userModelOptional.get();
        userModel.setPassword(userDto.getPassword());
        userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        this.userService.save(userModel);

        return ResponseEntity.ok().body(userModel);
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable UUID userId,
            @RequestBody @Validated(UserDto.View.ImagePut.class) @JsonView(UserDto.View.ImagePut.class) UserDto userDto) {
        var userModelOptional = this.userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userModel = userModelOptional.get();
        userModel.setImageUrl(userDto.getImageUrl());
        userModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        this.userService.save(userModel);

        return ResponseEntity.ok().body(userModel);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        var userModel = this.userService.findById(userId);

        if (userModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

}