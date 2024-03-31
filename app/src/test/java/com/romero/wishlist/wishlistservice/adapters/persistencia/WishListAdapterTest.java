package com.romero.wishlist.wishlistservice.adapters.persistencia;

import com.romero.wishlist.wishlistservice.adapters.persistencia.repository.FavoritoRepository;
import com.romero.wishlist.wishlistservice.application.exceptions.FavoritoNotFoundException;
import com.romero.wishlist.wishlistservice.mocks.WishlistMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WishListAdapterTest {

    @Mock
    FavoritoRepository wishlistRepository;
    @InjectMocks
    FavoritoAdapter favoritoAdapter;

    @Test
    @DisplayName("Verificar se os o metodos necessários para consulta estão sendo invocados corretamente")
    void testConsultarWishlistPorCliente() {
        Mockito.when(wishlistRepository.findByIdCliente(WishlistMocks.ID_CLIENTE)).thenReturn(WishlistMocks.getListWishlistEntity());
        favoritoAdapter.consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE);
        verify(wishlistRepository, times(1)).findByIdCliente(WishlistMocks.ID_CLIENTE);
    }

    @Test
    @DisplayName("Consultando wishlist, o retorno null da lista ")
    void testConsultarWishlistNulaPorCliente() {
        Mockito.when(wishlistRepository.findByIdCliente(WishlistMocks.ID_CLIENTE)).thenReturn(null);
        var lista = favoritoAdapter.consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE);
        verify(wishlistRepository, times(1)).findByIdCliente(WishlistMocks.ID_CLIENTE);
        assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Consultando wishlist vazia para o cliente")
    void testConsultarWishlistVaziaPorCliente() {
        Mockito.when(wishlistRepository.findByIdCliente(WishlistMocks.ID_CLIENTE)).thenReturn(List.of());
        var lista = favoritoAdapter.consultarFavoritoPorCliente(WishlistMocks.ID_CLIENTE);
        verify(wishlistRepository, times(1)).findByIdCliente(WishlistMocks.ID_CLIENTE);
        assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Verifica se o metodo consultar wishlist total por clientes está sendo invocado corretamente")
    void testConsultarWishlistTotalPorCliente() {
        var esperado = 0l;
        Mockito.when(wishlistRepository.countByIdCliente(WishlistMocks.ID_CLIENTE)).thenReturn(0l);
        var total = favoritoAdapter.consultarFavoritoTotalPorCliente(WishlistMocks.ID_CLIENTE);
        verify(wishlistRepository, times(1)).countByIdCliente(WishlistMocks.ID_CLIENTE);
        assertEquals(esperado, total);
    }

    @Test
    @DisplayName("Verifica se o metedo que irá persistir a wishlist na base de dados está sendo invocado corretamente")
    void testSalvarWishlist() {
        Mockito.when(wishlistRepository.save(WishlistMocks.getWishlistEntity())).thenReturn(WishlistMocks.getWishlistEntity());
        favoritoAdapter.salvar(WishlistMocks.getWishlist());
        verify(wishlistRepository, times(1)).save(WishlistMocks.getWishlistEntity());
    }

    @Test
    @DisplayName("Verifica se o metedo que irá persistir a wishlist na base de dados está sendo invocado corretamente")
    void testRemoverWishlist() {
        Mockito.doNothing().when(wishlistRepository).delete(WishlistMocks.getWishlistEntity());
        favoritoAdapter.removerFavorito(WishlistMocks.getWishlist());
        verify(wishlistRepository, times(1)).delete(WishlistMocks.getWishlistEntity());
    }

    @Test
    @DisplayName("Busca produto na wishlist do cliente com sucesso")
    void testBuscaPorIdComSucesso(){
        Mockito.when(wishlistRepository.findById(WishlistMocks.ID_WISHLIST)).thenReturn(Optional.of(WishlistMocks.getWishlistEntity()));
        var retorno = favoritoAdapter.buscarFavorito(WishlistMocks.ID_WISHLIST);
        verify(wishlistRepository, times(1)).findById(WishlistMocks.ID_WISHLIST);
        assertEquals(WishlistMocks.getWishlist(), retorno);

    }

    @Test
    @DisplayName("Busca produto na wishlist com retorno vazio")
    void testBuscaPorIdRetornoVazio(){
        Mockito.when(wishlistRepository.findById(WishlistMocks.ID_WISHLIST)).thenReturn(Optional.empty());

        assertThrows(FavoritoNotFoundException.class, () -> {
            favoritoAdapter.buscarFavorito(WishlistMocks.ID_WISHLIST);
        });
        verify(wishlistRepository, times(1)).findById(WishlistMocks.ID_WISHLIST);

    }
}