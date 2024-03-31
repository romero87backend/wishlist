package com.romero.wishlist.wishlistservice.application.exceptions;

public class IdGeradorException extends RuntimeException{
    public IdGeradorException(String msg, Throwable e){
        super(msg, e);
    }
}
