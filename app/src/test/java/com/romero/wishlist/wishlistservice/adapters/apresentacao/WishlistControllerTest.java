package com.romero.wishlist.wishlistservice.adapters.apresentacao;

import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.BuscaWishlistResponsetDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoRequestDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoResponseDTO;
import com.romero.wishlist.wishlistservice.core.service.WishlistService;
import com.romero.wishlist.wishlistservice.mocks.WishlistMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    @Mock
    WishlistService wishlistService;

    @InjectMocks
    WishlistController wishlistController;

    @Test
    @DisplayName("Teste adicionar favorito na wishlist")
    void testAdicionadoFavoritoComSucesso() {
        Mockito.when(wishlistService.adicionarFavoritoNaWishlist(any(FavoritoRequestDTO.class))).thenReturn(WishlistMocks.getWishlistDTO());
        var favoritoRequest = WishlistMocks.getWishlistProdutoRequestDTO();
        var response = wishlistController.adicionadoFavorito(favoritoRequest);
        verify(wishlistService, times(1)).adicionarFavoritoNaWishlist(favoritoRequest);

        assertEquals(WishlistMocks.DATA_CRIACAO, response.getDataCriacao());
        assertEquals(WishlistMocks.ID_CLIENTE, response.getIdCliente());
        assertEquals(WishlistMocks.ID_PRODUTO, response.getIdProduto());
        assertEquals(WishlistMocks.ID_WISHLIST, response.getId());

    }

    @Test
    @DisplayName("Testar remoção do favorito da wishlist")
    void testRemoverFavoritoComSucesso(){
        Mockito.doNothing().when(wishlistService).removerFavoritoDaWishList(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);
        wishlistController.removerFavorito(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);

        verify(wishlistService, times(1)).removerFavoritoDaWishList(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);
    }

    @Test
    @DisplayName("Testar consulta da wishlist do cliente, deve retornar os produtos favoritos do cliente com sucesso")
    void testConsultaWishlistDoClienteComSucesso(){
        var lista = WishlistMocks.getListWishlistDTO();
        var esperado = getEsperado(lista);
        Mockito.when(wishlistService.buscarWishlistDoCliente(WishlistMocks.ID_CLIENTE)).thenReturn(lista);
        var busca = wishlistController.buscarWishlistDoCliente(WishlistMocks.ID_CLIENTE);

        verify(wishlistService, times(1)).buscarWishlistDoCliente(WishlistMocks.ID_CLIENTE);

        assertEquals(esperado, busca);
    }

    @Test
    @DisplayName("Testar consultar favorito na wishlist do cliente, retornando favorito com sucesso")
    void testConsultaProdutoWishlistDoClienteComSucesso(){
        var mock = WishlistMocks.getWishlistDTO();
        var esperado = FavoritoResponseDTO.builder().id(mock.getId()).idCliente(mock.getIdCliente()).idProduto(mock.getIdProduto()).dataCriacao(mock.getDataCriacao()).build();

        Mockito.when(wishlistService.buscarFavoritoWishlistCliente(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO)).thenReturn(mock);
        var wishlist = wishlistController.buscarFavoritoNaWishlist(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);

        verify(wishlistService, times(1)).buscarFavoritoWishlistCliente(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);

        assertEquals(esperado, wishlist);
    }

    private BuscaWishlistResponsetDTO getEsperado(List<FavoritoDTO> listaDTO){
        var lista = listaDTO.stream().map(w -> FavoritoResponseDTO.builder().id(w.getId())
                .idCliente(w.getIdCliente())
                .idProduto(w.getIdProduto())
                .dataCriacao(w.getDataCriacao()).build())
                .collect(Collectors.toList());

        return BuscaWishlistResponsetDTO.builder().total(lista.size()).wishlist(lista).build();
    }
}