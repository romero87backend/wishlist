package com.romero.wishlist.wishlistservice.adapters.externo;

import com.romero.wishlist.wishlistservice.application.ports.ConsultaParametrosWishlistPort;
import org.springframework.stereotype.Component;

@Component
public class ConsultaParametrosWishlistAdapter implements ConsultaParametrosWishlistPort {

    private static final Integer MAXIMO_PRODUTOS_WISHLIST = 20;
    @Override
    public Integer consultarParametroNumeroMaximoProdutosLista() {
        /**
         * #TODO Implementar aqui chamada a API de parametros
         */

        return MAXIMO_PRODUTOS_WISHLIST;
    }
}
