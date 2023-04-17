package com.bda.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.bda.userservice.model.FavouritesEntity} entity
 */
@Data
@Builder
public class UserFavouritesDto implements Serializable{
    private final int userId;
    private final List<String> memeIds;
}
