package com.babyblackdog.ddogdog.wishlist.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.wishlist.application.WishlistFacade;
import com.babyblackdog.ddogdog.wishlist.controller.dto.WishlistResponse;
import com.babyblackdog.ddogdog.wishlist.controller.dto.WishlistResponses;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reviews")
public class WishlistRestController {
    private final WishlistFacade facade;

    public WishlistRestController(WishlistFacade facade) {
        this.facade = facade;
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody Long placeId) {
        JwtSimpleAuthentication jwt = JwtSimpleAuthentication.getInstance();
        String email = jwt.getEmail();

        WishlistResult addedWishlistResult = facade.registerWishlist(email, placeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WishlistResponse.of(addedWishlistResult));
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> removeWishlist(@PathVariable Long wishlistId) {
        facade.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistResponses> getWishlistsForMe(Pageable pageable) {
        JwtSimpleAuthentication jwt = JwtSimpleAuthentication.getInstance();
        String email = jwt.getEmail();

        WishlistResults retrievedReviewsResult = facade.findWishlistsByEmail(email, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WishlistResponses.of(retrievedReviewsResult));
    }
}