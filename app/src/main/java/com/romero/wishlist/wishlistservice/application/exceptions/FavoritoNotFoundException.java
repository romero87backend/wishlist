package com.romero.wishlist.wishlistservice.application.exceptions;

public class FavoritoNotFoundException extends RuntimeException {
    public FavoritoNotFoundException(String msg) {
        super(msg);
    }
}
