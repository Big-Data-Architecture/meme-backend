package com.bda.memebackend.controller;

import com.bda.memebackend.model.UserEntity;
import com.bda.memebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
  @Autowired
  UserService userService;

  @PostMapping(value = "/users")
  public UserEntity createUser(@RequestBody UserEntity user) {
    return userService.createUser(user);
  }

  @GetMapping(value = "/users")
  public List<UserEntity> getUsers() {
    return userService.getUsers();
  }

  @GetMapping(value = "/users/{userId}")
  public Optional<UserEntity> getUser(@PathVariable Integer userId) {
    return userService.getUser(userId);
  }
}
