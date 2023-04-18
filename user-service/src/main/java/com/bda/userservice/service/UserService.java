package com.bda.userservice.service;

import com.bda.userservice.dto.UserEntityDto;
import com.bda.userservice.exception.UserExistsException;
import com.bda.userservice.model.UserEntity;
import com.bda.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity createUser(UserEntityDto user) throws UserExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException("User with username: " + user.getUsername() + " exists");
        } else {
            return userRepository.save(UserEntity
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .lastname(user.getLastname())
                    .firstname(user.getFirstname())
                    .build());
        }
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUser(Integer userId) {
        return userRepository.findById(userId);
    }

    public UserEntity getUserByName(String username) {
        return userRepository.findUserEntityByUsername(username);
    }
}
