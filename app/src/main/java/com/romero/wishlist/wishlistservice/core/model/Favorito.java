package com.romero.wishlist.wishlistservice.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Favorito {
    private String id;
    private String idCliente;
    private String idProduto;
    private LocalDateTime dataCriacao;
}
