package com.bda.userservice.controller;

import com.bda.userservice.service.ElasticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"}, allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.HEAD})
@RequestMapping("api/v1/search")
public class ElasticController {

    @Autowired
    ElasticService elasticService;

    private final Logger logger = LoggerFactory.getLogger(ElasticController.class.getSimpleName());

    @GetMapping("/memes/")
    public ResponseEntity<String> searchMemes(@RequestParam(value = "query", required = true) String query,
                                              @RequestParam(value = "type", required = false, defaultValue = "gen") String type) {
        if (query.isBlank()) return ResponseEntity.badRequest().build();

        try {
            String responseBody = "";
            if (type.equals("ml")) {
                responseBody = elasticService.knnSearch(query);
            } else {
                responseBody = elasticService.generalSearch(query);
            }
            logger.debug(responseBody);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/meme_of_the_day")
    public ResponseEntity<String> getMemeOfTheDay() {
        try {
            String response = elasticService.searchRandom();
            logger.debug(response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/meme_info/")
    public ResponseEntity<String> getMemeByIds(@RequestParam("ids") List<String> ids) {
        try {
            String response = elasticService.getMemesByIds(ids);
            logger.debug(response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
