package com.bda.userservice.controller;

import com.bda.userservice.dto.FavouritesEntityDto;
import com.bda.userservice.dto.UserFavouritesDto;
import com.bda.userservice.dto.IsFavouritesEntityDto;
import com.bda.userservice.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"}, allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.HEAD})
@RequestMapping("/api/v1")
public class FavouritesController {

    @Autowired
    FavouritesService favouritesService;

    @PostMapping("/favourites")
    public ResponseEntity<IsFavouritesEntityDto> toggleFavourites(@RequestBody FavouritesEntityDto favouritesEntityDto){
        return ResponseEntity.ok(favouritesService.toggleFavourites(favouritesEntityDto));
    }

    @GetMapping(value = {"/favourites/{userId}"})
    @Cacheable("favourites")
    public ResponseEntity<UserFavouritesDto> getFavourites(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(favouritesService.getFavourites(userId));
    }

    @GetMapping("/favourites/{userId}/{memeId}")
    @Cacheable("isFavourites")
    public ResponseEntity<IsFavouritesEntityDto> isFavourites(@PathVariable("userId") Integer userId,
                                                          @PathVariable("memeId") String memeId){
        return ResponseEntity.ok(favouritesService.isFavourite(userId,memeId));
    }

}
