package com.romero.wishlist.wishlistservice.adapters.externo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ConsultaParametrosFavoritoAdapterTest {


    @Test
    void consultarParametroNumeroMaximoProdutosLista() {
        var consultaParametros = new ConsultaParametrosWishlistAdapter();
        assertEquals(20, consultaParametros.consultarParametroNumeroMaximoProdutosLista());
    }
}