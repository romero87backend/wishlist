package com.romero.wishlist.wishlistservice.adapters.apresentacao.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BuscaWishlistResponsetDTO {

    private Integer total;
    private List<FavoritoResponseDTO> wishlist;
}
