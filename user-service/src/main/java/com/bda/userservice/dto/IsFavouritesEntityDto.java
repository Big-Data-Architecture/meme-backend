package com.bda.userservice.dto;

import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bda.userservice.model.FavouritesEntity} entity
 */
@Data
@Builder
public class IsFavouritesEntityDto implements Serializable {
    private final int userId;
    private final String memeId;
    private final boolean isFavourite;
}