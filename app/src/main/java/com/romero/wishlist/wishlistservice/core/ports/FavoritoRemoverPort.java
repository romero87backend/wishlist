package com.romero.wishlist.wishlistservice.core.ports;

import com.romero.wishlist.wishlistservice.core.model.Favorito;

public interface FavoritoRemoverPort {

    void removerFavorito(Favorito favorito);
}
