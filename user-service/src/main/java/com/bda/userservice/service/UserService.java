package com.bda.userservice.service;

import com.bda.userservice.dto.UserEntityDto;
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

  public UserEntity createUser(UserEntityDto user) {
    UserEntity newUser = new UserEntity();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(user.getPassword());
    return userRepository.save(newUser);
  }

  public List<UserEntity> getUsers() {
    return userRepository.findAll();
  }

  public Optional<UserEntity> getUser(Integer userId) {
    return userRepository.findById(userId);
  }
}
