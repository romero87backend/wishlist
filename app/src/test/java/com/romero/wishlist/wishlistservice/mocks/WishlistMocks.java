package com.romero.wishlist.wishlistservice.mocks;

import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoDTO;
import com.romero.wishlist.wishlistservice.adapters.apresentacao.dto.FavoritoRequestDTO;
import com.romero.wishlist.wishlistservice.adapters.persistencia.entidade.FavoritoEntity;
import com.romero.wishlist.wishlistservice.application.helper.IDdHelper;
import com.romero.wishlist.wishlistservice.core.model.Favorito;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class WishlistMocks {

    private WishlistMocks(){}

    public static final String ID_CLIENTE = "50651d4d-c4f1-4d61-aae5-838f7cb8989c";
    public static final String ID_PRODUTO = "4d454da7-3858-483d-b7c3-10b541f4a394";

    public static final String ID_WISHLIST = "a52d629d2373807ed18781fe3197dcce7b550cfa7900ec1d4cd1707e45f4a714";

    public static final LocalDateTime DATA_CRIACAO = LocalDateTime.of(2024, 3, 29, 10, 30, 10);
    public static FavoritoRequestDTO getWishlistProdutoRequestDTO(){
        return FavoritoRequestDTO.builder().idProduto(ID_PRODUTO).idCliente(ID_CLIENTE).build();
    }

    public static FavoritoEntity getWishlistEntity(){
        return new FavoritoEntity(ID_WISHLIST, ID_CLIENTE, ID_PRODUTO, DATA_CRIACAO);
    }

    public static Favorito getWishlist(){
        return Favorito.builder().id(ID_WISHLIST).idCliente(ID_CLIENTE).idProduto(ID_PRODUTO).dataCriacao(DATA_CRIACAO).build();
    }

    public static FavoritoDTO getWishlistDTO() {

        return FavoritoDTO.builder().id(ID_WISHLIST).idCliente(ID_CLIENTE).idProduto(ID_PRODUTO).dataCriacao(DATA_CRIACAO).build();
    }

    public static List<Favorito> getListWishlist(){

        var tamanhoDaLista = 20;
        var lista = new ArrayList<Favorito>();
        for(int i = 1; i <= tamanhoDaLista; i++){
            var mes =  ThreadLocalRandom.current().nextInt(1, 13);
            var idProduto = UUID.randomUUID().toString();
            lista.add(Favorito.builder().idCliente(IDdHelper.gerarID(ID_CLIENTE, idProduto))
                    .idCliente(ID_CLIENTE).idProduto(idProduto).dataCriacao(LocalDateTime.of(2024, mes, i, 10, 30, 05 )).build());
        }

        return lista;
    }

    public static List<FavoritoEntity> getListWishlistEntity(){
        var tamanhoDaLista = 20;
        var lista = new ArrayList<FavoritoEntity>();
        for(int i = 1; i <= tamanhoDaLista; i++){
            var mes =  ThreadLocalRandom.current().nextInt(1, 13);
            var idProduto = UUID.randomUUID().toString();
            var entity = new FavoritoEntity(IDdHelper.gerarID(ID_CLIENTE, idProduto), ID_CLIENTE, idProduto, LocalDateTime.of(2024, mes, i, 10, 30, 05 ));
            lista.add(entity);

        }
        return lista;
    }

    public static List<FavoritoDTO> getListWishlistDTO(){
        var tamanhoDaLista = 20;
        var lista = new ArrayList<FavoritoDTO>();
        for(int i = 1; i <= tamanhoDaLista; i++){
            var mes =  ThreadLocalRandom.current().nextInt(1, 13);
            var idProduto = UUID.randomUUID().toString();
            lista.add(FavoritoDTO.builder()
                    .id(IDdHelper.gerarID(ID_CLIENTE, idProduto))
                    .idCliente(ID_CLIENTE).idProduto(ID_PRODUTO)
                    .dataCriacao(LocalDateTime.of(2024, mes, i, 10, 30, 05)).build());

        }
        return lista;
    }
}
