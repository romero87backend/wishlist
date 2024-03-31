package com.romero.wishlist.wishlistservice.adapters.apresentacao.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FavoritoDTO {

    private String id;
    private String idCliente;
    private String idProduto;
    private LocalDateTime dataCriacao;
}
