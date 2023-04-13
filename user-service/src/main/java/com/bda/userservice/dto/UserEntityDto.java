package com.bda.userservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bda.userservice.model.UserEntity} entity
 */
@Data
public class UserEntityDto implements Serializable {
    private final String username;
    private final String password;
}