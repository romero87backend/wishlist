package com.romero.wishlist.wishlistservice.adapters.apresentacao;

import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.BuscaWishlistResponsetDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoRequestDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoResponseDTO;
import com.romero.wishlist.wishlistservice.core.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public FavoritoResponseDTO adicionadoFavorito(@RequestBody FavoritoRequestDTO wishlistProdutoRequestDTO){
        var favorito = wishlistService.adicionarFavoritoNaWishlist(wishlistProdutoRequestDTO);
        return FavoritoResponseDTO.builder().id(favorito.getId()).idCliente(favorito.getIdCliente())
                .idProduto(favorito.getIdProduto()).dataCriacao(favorito.getDataCriacao()).build();
    }

    @DeleteMapping("/wishlist/{id_cliente}/{id_produto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFavorito(@PathVariable("id_cliente") String idCliente,
                                @PathVariable("id_produto") String idProduto) {
        wishlistService.removerFavoritoDaWishList(idCliente, idProduto);
    }

    @GetMapping("/wishlist/{id_cliente}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BuscaWishlistResponsetDTO buscarWishlistDoCliente(@PathVariable("id_cliente") String idCliente){
        var response = wishlistService.buscarWishlistDoCliente(idCliente);

        var wishlist = response.stream().map(r -> FavoritoResponseDTO.builder().id(r.getId()).idCliente(r.getIdCliente())
                .idProduto(r.getIdProduto()).dataCriacao(r.getDataCriacao()).build()).collect(Collectors.toList());

        return BuscaWishlistResponsetDTO.builder().total(wishlist.size()).wishlist(wishlist).build();
    }

    @GetMapping("/wishlist/{id_cliente}/{id_produto}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FavoritoResponseDTO buscarFavoritoNaWishlist(@PathVariable("id_cliente") String idCliente,
                                                        @PathVariable("id_produto") String idProduto) {
        var favorito = wishlistService.buscarFavoritoWishlistCliente(idCliente, idProduto);

        return FavoritoResponseDTO.builder().id(favorito.getId()).idCliente(favorito.getIdCliente())
                .idProduto(favorito.getIdProduto()).dataCriacao(favorito.getDataCriacao()).build();
    }
}
