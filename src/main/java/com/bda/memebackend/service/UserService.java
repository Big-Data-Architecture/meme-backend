package com.bda.memebackend.service;

import com.bda.memebackend.model.UserEntity;
import com.bda.memebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public UserEntity createUser(UserEntity user) {
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
