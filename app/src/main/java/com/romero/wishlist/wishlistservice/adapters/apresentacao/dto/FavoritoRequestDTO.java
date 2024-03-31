package com.romero.wishlist.wishlistservice.adapters.apresentacao.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FavoritoRequestDTO {

    private String idCliente;
    private String idProduto;
}
