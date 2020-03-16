package com.rosicleiafrasson.moviecatalogservice.resorces;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.rosicleiafrasson.moviecatalogservice.models.CatalogItem;
import com.rosicleiafrasson.moviecatalogservice.models.Movie;
import com.rosicleiafrasson.moviecatalogservice.models.Rating;
import com.rosicleiafrasson.moviecatalogservice.models.UserRating;
import com.rosicleiafrasson.moviecatalogservice.services.MovieInfo;
import com.rosicleiafrasson.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

    WebClient.Builder builder = WebClient.builder();

      UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings.getRatings().stream()
                .map(rating ->  movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());

    }





}
