package com.mvp.crudmicroservice.user.web.controller;


import com.mvp.crudmicroservice.user.domain.exception.AccountAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.service.AccountService;
import com.mvp.crudmicroservice.user.service.UserService;
import com.mvp.crudmicroservice.user.web.dto.auth.JwtRequest;
import com.mvp.crudmicroservice.user.web.dto.user.AccountDto;
import com.mvp.crudmicroservice.user.web.dto.user.UserDto;
import com.mvp.crudmicroservice.user.web.mapper.AccountMapper;
import com.mvp.crudmicroservice.user.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crud/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name="UserController", description="Работа с аккаунтом в пользователя")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Operation(
            summary = "Создание пользователя",
            description = "Создает аккаунт пользователя в системе"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Parameter(description = "Сущность пользователя") @RequestBody UserDto userDto) {
        try {
            User user = userMapper.toEntity(userDto);
            User createdUser = userService.create(user);

            return ResponseEntity.ok().body(userMapper.toDto(createdUser));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(
            summary = "Получение пользователя???",
            description = "Получает сущность пользователя???"
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> getUser(@Parameter(description = "???") @RequestBody JwtRequest jwtRequest) {
        try {
            User user = userService.getByUsername(jwtRequest.getUsername());
            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Создание акаунта в API???",
            description = "Создает аккаунт в API инвестиций???"
    )
    @PostMapping("/accounts")
    public ResponseEntity createAccount(@Parameter(description = "???") @RequestBody AccountDto accountDto) {
        try {
            Account account = accountMapper.toEntity(accountDto);
            Account createdAccount = accountService.create(account);

            return ResponseEntity.ok().body(accountMapper.toDto(createdAccount));
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("error-message", exception.getMessage())
                    .body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Обновление аккаунта пользователя",
            description = "Обновляет информацию о конкретном пользователе"
    )
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Parameter(description = "Сущность пользователя") @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(userMapper.toDto(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Получение аккаунта по ID инвестиций",
            description = "Получает полную информацию об аккаунте пользователя"
    )
    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@Parameter(description = "ID пользователя") @PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("error-message", e.getMessage())
                    .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Получение пользователя по username/e-mail",
            description = "Получает информацию о пользователе"
    )
    @GetMapping
    public ResponseEntity<UserDto> getUserByUsername(@Parameter(description = "username/e-mail") @RequestParam(value = "username") String username) {
        try {
            User user = userService.getByUsername(username);
            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: удалять плюсом акк инвестиций???
    @Operation(
            summary = "Удаляет аккаунт пользователя по ID",
            description = "Получает информацию о пользователе"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@Parameter(description = "ID пользователя") @PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().body("Пользователь успешно удалён");
    }

}