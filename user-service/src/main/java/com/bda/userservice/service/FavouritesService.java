package com.bda.userservice.service;

import com.bda.userservice.dto.FavouritesEntityDto;
import com.bda.userservice.dto.IsFavouritesEntityDto;
import com.bda.userservice.dto.UserFavouritesDto;
import com.bda.userservice.model.FavouritesEntity;
import com.bda.userservice.model.FavouritesEntityPK;
import com.bda.userservice.repository.FavouritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavouritesService {

    @Autowired
    FavouritesRepository favouritesRepository;

    @CacheEvict(value = {"getFavourites","isFavourites"},allEntries = true)
    public IsFavouritesEntityDto toggleFavourites(FavouritesEntityDto favouritesEntityDto) {
        if (favouriteExists(favouritesEntityDto.getUserId(), favouritesEntityDto.getMemeId())) {
            favouritesRepository
                    .deleteByUserIdAndMemeId(favouritesEntityDto.getUserId(), favouritesEntityDto.getMemeId());
            return IsFavouritesEntityDto
                    .builder()
                    .memeId(favouritesEntityDto.getMemeId())
                    .userId(favouritesEntityDto.getUserId())
                    .isFavourite(false)
                    .build();
        } else {
            FavouritesEntity favouritesEntity = favouritesRepository.save(FavouritesEntity
                    .builder()
                    .userId(favouritesEntityDto.getUserId())
                    .memeId(favouritesEntityDto.getMemeId())
                    .build());
            return IsFavouritesEntityDto
                    .builder()
                    .memeId(favouritesEntity.getMemeId())
                    .userId(favouritesEntity.getUserId())
                    .isFavourite(true)
                    .build();
        }
    }

    @Cacheable("getFavourites")
    public UserFavouritesDto getFavourites(Integer userId) {
        return UserFavouritesDto
                .builder()
                .userId(userId)
                .memeIds(favouritesRepository
                        .findByUserId(userId)
                        .stream()
                        .map((FavouritesEntity::getMemeId))
                        .collect(Collectors.toList()))
                .build();
    }

    @Cacheable("isFavourites")
    public IsFavouritesEntityDto isFavourite(Integer userId, String memeId) {
        return IsFavouritesEntityDto
                .builder()
                .isFavourite(favouriteExists(userId, memeId))
                .userId(userId)
                .memeId(memeId)
                .build();
    }

    private boolean favouriteExists(Integer userId, String memeId) {
        return favouritesRepository.existsById(FavouritesEntityPK.builder().memeId(memeId).userId(userId).build());
    }
}
