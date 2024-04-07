package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_NewUser_ReturnsCreatedUser() {

        String username = "test_username";
        User newUser = new User();
        newUser.setUsername(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);


        User createdUser = userService.create(newUser);


        Assertions.assertEquals(newUser, createdUser);
    }

    @Test
    void testCreate_ExistingUser_ThrowsUserAlreadyExistsException() {

        String username = "existing_username";
        User existingUser = new User();
        existingUser.setUsername(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));


        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(existingUser));
    }

    @Test
    void testGetById_UserExists_ReturnsUser() {

        long userId = 1;
        User user = new User();
        user.setId(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        User fetchedUser = userService.getById(userId);


        Assertions.assertEquals(userId, fetchedUser.getId());
    }

    @Test
    void testGetById_UserDoesNotExist_ThrowsResourceNotFoundException() {

        long userId = 1;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void testGetByUsername_UserExists_ReturnsUser() {

        String username = "test_username";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        User fetchedUser = userService.getByUsername(username);


        Assertions.assertEquals(username, fetchedUser.getUsername());
    }

    @Test
    void testGetByUsername_UserDoesNotExist_ThrowsResourceNotFoundException() {

        String username = "non_existing_username";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getByUsername(username));
    }

    @Test
    void testUpdate_UserExists_ReturnsUpdatedUser() {

        User user = new User();
        user.setId(1L);
        Mockito.when(userRepository.save(user)).thenReturn(user);


        User updatedUser = userService.update(user);


        Assertions.assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    void testDelete_UserExists_DeletesUser() {

        long userId = 1;
        Mockito.doNothing().when(userRepository).deleteById(userId);


        Assertions.assertDoesNotThrow(() -> userService.delete(userId));
    }

}
