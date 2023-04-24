package com.bda.userservice.controller;

import com.bda.userservice.dto.UserEntityDto;
import com.bda.userservice.exception.UserExistsException;
import com.bda.userservice.model.UserEntity;
import com.bda.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"}, allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.HEAD})
@RequestMapping("/api/v1")
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  CacheManager cacheManager;

  @PostMapping(value="/signup")
  public ResponseEntity<UserEntity> signup(@RequestBody UserEntityDto user){
    return this.createUser(user);
  }

  @PostMapping(value="/login")
  public ResponseEntity<UserEntity> login(@RequestBody UserEntityDto user){
    UserEntity userEntity = userService.getUserByName(user.getUsername());
    if(!Objects.isNull(userEntity) && userEntity.getPassword().equals(user.getPassword())){
        return ResponseEntity.ok(userEntity);
    }else{
        return new ResponseEntity<>(new UserEntity(), HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping(value = "/users")
  public ResponseEntity<UserEntity> createUser(@RequestBody UserEntityDto user) {
    try{
      return ResponseEntity.ok(userService.createUser(user));
    }catch(UserExistsException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/users")
  public ResponseEntity<List<UserEntity>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping(value = "/users/{userId}")
  public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable Integer userId) {
    return ResponseEntity.ok(userService.getUser(userId));
  }

  @GetMapping(value = "/clearcache")
  public void evictAllCaches(){
    cacheManager.getCacheNames()
            .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
  }
}
