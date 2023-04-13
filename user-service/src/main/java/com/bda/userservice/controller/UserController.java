package com.bda.userservice.controller;

import com.bda.userservice.dto.UserEntityDto;
import com.bda.userservice.model.UserEntity;
import com.bda.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
  @Autowired
  UserService userService;

  @PostMapping(value = "/users")
  public ResponseEntity<UserEntity> createUser(@RequestBody UserEntityDto user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @GetMapping(value = "/users")
  public ResponseEntity<List<UserEntity>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping(value = "/users/{userId}")
  public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable Integer userId) {
    return ResponseEntity.ok(userService.getUser(userId));
  }
}
