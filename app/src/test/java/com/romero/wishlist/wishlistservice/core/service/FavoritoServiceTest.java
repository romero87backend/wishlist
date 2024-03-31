package com.romero.wishlist.wishlistservice.core.service;


import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoDTO;
import com.romero.wishlist.wishlistservice.application.ports.ConsultaParametrosWishlistPort;
import com.romero.wishlist.wishlistservice.core.model.Favorito;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoConsultarPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoRemoverPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoSalvaPort;
import com.romero.wishlist.wishlistservice.mocks.WishlistMocks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceTest {

    @Mock
    ConsultaParametrosWishlistPort consultaParametrosWishlistPort;
    @Mock
    FavoritoConsultarPort favoritoConsultarPort;
    @Mock
    FavoritoSalvaPort favoritoSalvaPort;
    @Mock
    FavoritoRemoverPort wishlistRemoverPort;
    @InjectMocks
    WishlistService wishlistService;


    @Test
    @DisplayName("teste para inserir novos produtos na lista de favoritos, o produto deve ser inserido com sucesso")
    void testeAdicionarProdutosAosFavoritosComSucesso() {


        Mockito.when(consultaParametrosWishlistPort.consultarParametroNumeroMaximoProdutosLista()).thenReturn(20);
        Mockito.when(favoritoConsultarPort.consultarFavoritoTotalPorCliente(any(String.class))).thenReturn(2l);


        var wishlistProduto = WishlistMocks.getWishlistProdutoRequestDTO();
        var wishlist = wishlistService.adicionarFavoritoNaWishlist(wishlistProduto);

        verify(consultaParametrosWishlistPort, times(1)).consultarParametroNumeroMaximoProdutosLista();
        verify(favoritoConsultarPort, times(1)).consultarFavoritoTotalPorCliente(WishlistMocks.ID_CLIENTE);
        verify(favoritoSalvaPort, times(1)).salvar(any(Favorito.class));


        assertEquals(WishlistMocks.ID_WISHLIST, wishlist.getId());
        assertEquals(WishlistMocks.ID_CLIENTE, wishlist.getIdCliente());
        assertEquals(WishlistMocks.ID_PRODUTO, wishlist.getIdProduto());
        assertNotNull(wishlist.getDataCriacao());
    }

    @Test
    @DisplayName("teste para inserir novos produtos na lista de favoritos que já está no limite configurado, deve remover o favorito mais antigo e adicionar o atual")
    void testeAdicionarProdutos() {

        var listaWishlistMock = WishlistMocks.getListWishlist();
        var primeiroItemLista = listaWishlistMock.stream().sorted(Comparator.comparing(Favorito::getDataCriacao)).findFirst().get();

        Mockito.when(consultaParametrosWishlistPort.consultarParametroNumeroMaximoProdutosLista()).thenReturn(20);
        Mockito.when(favoritoConsultarPort.consultarFavoritoTotalPorCliente(any(String.class))).thenReturn(20l);
        Mockito.when(favoritoConsultarPort.consultarFavoritoPorCliente(any(String.class))).thenReturn(listaWishlistMock);
        Mockito.doNothing().when(wishlistRemoverPort).removerFavorito(primeiroItemLista);


        var wishlistProduto = WishlistMocks.getWishlistProdutoRequestDTO();
        var wishlist = wishlistService.adicionarFavoritoNaWishlist(wishlistProduto);
        verify(consultaParametrosWishlistPort, times(1)).consultarParametroNumeroMaximoProdutosLista();
        verify(favoritoConsultarPort, times(1)).consultarFavoritoTotalPorCliente(WishlistMocks.ID_CLIENTE);
        verify(favoritoConsultarPort, times(1)).consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE);
        verify(wishlistRemoverPort, times(1)).removerFavorito(primeiroItemLista);
        verify(favoritoSalvaPort, times(1)).salvar(any(Favorito.class));

        assertEquals(WishlistMocks.ID_WISHLIST, wishlist.getId());
        assertEquals(WishlistMocks.ID_CLIENTE, wishlist.getIdCliente());
        assertEquals(WishlistMocks.ID_PRODUTO, wishlist.getIdProduto());
        assertNotNull(wishlist.getDataCriacao());
    }

    @Test
    @DisplayName("Remover produto da wishlist do cliente")
    void testRemoverProdutoDaWishlistComSucesso(){
        var wishlistRemover = Favorito.builder().id(WishlistMocks.ID_WISHLIST)
                .idCliente(WishlistMocks.ID_CLIENTE)
                .idProduto(WishlistMocks.ID_PRODUTO).build();
        Mockito.doNothing().when(wishlistRemoverPort).removerFavorito(wishlistRemover);

        wishlistService.removerFavoritoDaWishList(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);

        verify(wishlistRemoverPort, times(1)).removerFavorito(wishlistRemover);
    }

    @Test
    @DisplayName("Consultar wishlist do cliente com sucesso")
    void testConsultarWishlistDoClienteComSucesso(){

        var listaMock = WishlistMocks.getListWishlist();
        var esperado = getEsperado(listaMock);
        Mockito.when(favoritoConsultarPort.consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE)).thenReturn(listaMock);

        var lista = wishlistService.buscarWishlistDoCliente(WishlistMocks.ID_CLIENTE);

        verify(favoritoConsultarPort, times(1)).consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE);

        assertEquals(esperado, lista);
    }

    @Test
    @DisplayName("Consultar produto na wishlist do cliente com sucesso")
    void testCosultarProdutoWishlistDoClienteComSucesso(){

        var esperado = WishlistMocks.getWishlistDTO();

        Mockito.when(favoritoConsultarPort.buscarFavorito(WishlistMocks.ID_WISHLIST)).thenReturn(WishlistMocks.getWishlist());

        var wishlist = wishlistService.buscarFavoritoWishlistCliente(WishlistMocks.ID_CLIENTE, WishlistMocks.ID_PRODUTO);

        verify(favoritoConsultarPort, times(1)).buscarFavorito(WishlistMocks.ID_WISHLIST);

        assertEquals(esperado, wishlist);
    }

    private List<FavoritoDTO> getEsperado(List<Favorito> lista){
        return lista.stream().map(w -> FavoritoDTO.builder().id(w.getId()).idCliente(w.getIdCliente()).idProduto(w.getIdProduto()).dataCriacao(w.getDataCriacao()).build()).collect(Collectors.toList());
    }
}