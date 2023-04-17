package com.bda.userservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bda.userservice.model.FavouritesEntity} entity
 */
@Data
public class FavouritesEntityDto implements Serializable {
    private final int userId;
    private final String memeId;
}