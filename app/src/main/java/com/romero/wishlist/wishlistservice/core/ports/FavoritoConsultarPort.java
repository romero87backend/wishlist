package com.romero.wishlist.wishlistservice.core.ports;

import com.romero.wishlist.wishlistservice.core.model.Favorito;

import java.util.List;

public interface FavoritoConsultarPort {

    List<Favorito> consultarFavoritoPorCliente(String idCliente);

    Long consultarFavoritoTotalPorCliente(String idCliente);

    Favorito buscarFavorito(String id);
}
