package com.romero.wishlist.wishlistservice.core.service;

import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoRequestDTO;
import com.romero.wishlist.wishlistservice.application.helper.IDdHelper;
import com.romero.wishlist.wishlistservice.core.model.Favorito;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoConsultarPort;
import com.romero.wishlist.wishlistservice.application.ports.ConsultaParametrosWishlistPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoRemoverPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoSalvaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class WishlistService {


    private final FavoritoConsultarPort favoritoConsultarPort;
    private final ConsultaParametrosWishlistPort consultaParametrosWishlistPort;
    private final FavoritoSalvaPort favoritoSalvaPort;
    private final FavoritoRemoverPort wishlistRemoverPort;

    public FavoritoDTO buscarFavoritoWishlistCliente(String idCliente, String idProduto){
        var produtoWishlist = favoritoConsultarPort.buscarFavorito(IDdHelper.gerarID(idCliente, idProduto));

        return FavoritoDTO.builder().id(produtoWishlist.getId())
                .idCliente(produtoWishlist.getIdCliente())
                .idProduto(produtoWishlist.getIdProduto())
                .dataCriacao(produtoWishlist.getDataCriacao()).build();
    }

    public List<FavoritoDTO> buscarWishlistDoCliente(String idCliente){
        var listaFavoritos = favoritoConsultarPort.consultarFavoritoPorCliente(idCliente);

        return listaFavoritos.stream().map(w -> FavoritoDTO.builder().id(w.getId())
                .idCliente(w.getIdCliente())
                .idProduto(w.getIdProduto()).dataCriacao(w.getDataCriacao()).build()).collect(Collectors.toList());
    }

    public void removerFavoritoDaWishList(String idCliente, String idProduto){
        wishlistRemoverPort.removerFavorito(Favorito.builder().id(IDdHelper.gerarID(idCliente,
                idProduto)).idCliente(idCliente).idProduto(idProduto).build());
    }

    public FavoritoDTO adicionarFavoritoNaWishlist(FavoritoRequestDTO favoritoDTO) {

        var maximoPossivelFavoritos = consultaParametrosWishlistPort.consultarParametroNumeroMaximoProdutosLista();
        var totalDeFavoritosDoCliente = favoritoConsultarPort.consultarFavoritoTotalPorCliente(favoritoDTO.getIdCliente());

        if (totalDeFavoritosDoCliente < maximoPossivelFavoritos) {
            return criarFavorito(favoritoDTO);
        }else {
            removerFavoritoMaisAntigo(favoritoDTO);
            return criarFavorito(favoritoDTO); 
        }
        
    }

    private void removerFavoritoMaisAntigo(FavoritoRequestDTO wishlistProdutoRequestDTO) {
        var listaWishlist = favoritoConsultarPort.consultarFavoritoPorCliente(wishlistProdutoRequestDTO.getIdCliente());
        var optionalFavoritoMaisAntigo = listaWishlist.stream().min(Comparator.comparing(Favorito::getDataCriacao));
        optionalFavoritoMaisAntigo.ifPresent(wishlistRemoverPort::removerFavorito);
    }

    private FavoritoDTO criarFavorito(FavoritoRequestDTO wishlistProdutoRequestDTO) {
        var wishlist = Favorito.builder().id(IDdHelper.gerarID(wishlistProdutoRequestDTO.getIdCliente(), wishlistProdutoRequestDTO.getIdProduto())).idCliente(wishlistProdutoRequestDTO.getIdCliente())
                .idProduto(wishlistProdutoRequestDTO.getIdProduto())
                .dataCriacao(LocalDateTime.now()).build();

        favoritoSalvaPort.salvar(wishlist);

        return FavoritoDTO.builder().id(wishlist.getId()).idCliente(wishlist.getIdCliente())
                .idProduto(wishlist.getIdProduto())
                .dataCriacao(wishlist.getDataCriacao()).build();
    }

}
