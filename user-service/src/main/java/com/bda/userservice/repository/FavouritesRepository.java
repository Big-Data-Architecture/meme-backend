package com.bda.userservice.repository;

import com.bda.userservice.model.FavouritesEntity;
import com.bda.userservice.model.FavouritesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FavouritesRepository extends JpaRepository<FavouritesEntity, FavouritesEntityPK> {
    List<FavouritesEntity> findByUserId(int userId);
    void deleteByUserIdAndMemeId(int userId, String memeId);
}
