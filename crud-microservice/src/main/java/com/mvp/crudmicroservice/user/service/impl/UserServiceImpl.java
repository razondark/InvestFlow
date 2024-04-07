package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.repository.UserRepository;
import com.mvp.crudmicroservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(final User newUser) {
        Optional<User> existUser = userRepository.findByUsername(newUser.getUsername());
        if (existUser.isEmpty()) {
            return userRepository.save(newUser);
        } else {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }
    }

    @Override
    public User getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no user with id %d", id)));
    }

    @Override
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователя с именем %s не существует", username)));
    }

    @Override
    public User update(final User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}