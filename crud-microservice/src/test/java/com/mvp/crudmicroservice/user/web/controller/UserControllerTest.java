package com.mvp.crudmicroservice.user.web.controller;

import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.service.UserService;
import com.mvp.crudmicroservice.user.web.dto.user.UserDto;
import com.mvp.crudmicroservice.user.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_ValidUser_ReturnsCreatedUserDto() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.createUser(userDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testCreateUser_UserAlreadyExists_ReturnsConflictStatus() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        doThrow(UserAlreadyExistsException.class).when(userService).create(user);

        // Act
        ResponseEntity<UserDto> response = userController.createUser(userDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
    // Код теста для метода updateUser
    @Test
    void testUpdateUser_ValidUser_ReturnsUpdatedUserDto() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.update(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testUpdateUser_UserNotFound_ReturnsNotFoundStatus() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.update(user)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
