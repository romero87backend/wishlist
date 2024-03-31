package com.romero.wishlist.wishlistservice.application.helper;

import com.romero.wishlist.wishlistservice.application.exceptions.FavoritoNotFoundException;
import com.romero.wishlist.wishlistservice.application.exceptions.IdGeradorException;
import com.romero.wishlist.wishlistservice.mocks.WishlistMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDdHelperTest {

    @Test
    @DisplayName("Gerar id com sucesso")
    void testGerarIdSucesso(){
        var esperado = "a52d629d2373807ed18781fe3197dcce7b550cfa7900ec1d4cd1707e45f4a714";
        var id = IDdHelper.gerarID(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);
        assertEquals(esperado, id);
    }

    @Test
    @DisplayName("Gerar id com erro")
    void testGerarIdErro(){

        assertThrows(IdGeradorException.class, () -> {
            IDdHelper.gerarID(null, null);
        });

    }
}