package com.romero.wishlist.wishlistservice.adapters.persistencia;

import com.romero.wishlist.wishlistservice.adapters.persistencia.entidade.FavoritoEntity;
import com.romero.wishlist.wishlistservice.adapters.persistencia.repository.FavoritoRepository;
import com.romero.wishlist.wishlistservice.application.exceptions.FavoritoNotFoundException;
import com.romero.wishlist.wishlistservice.core.model.Favorito;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoConsultarPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoRemoverPort;
import com.romero.wishlist.wishlistservice.core.ports.FavoritoSalvaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FavoritoAdapter implements FavoritoConsultarPort, FavoritoSalvaPort, FavoritoRemoverPort {

    private final FavoritoRepository favoritoRepository;

    @Override
    public List<Favorito> consultarFavoritoPorCliente(String idCliente) {
        var listaWishlist = favoritoRepository.findByIdCliente(idCliente);

        if(listaWishlist != null && !listaWishlist.isEmpty()){
            return listaWishlist.stream().map(w -> Favorito.builder().id(w.getId())
                    .idCliente(w.getIdCliente())
                    .idProduto(w.getIdProduto())
                    .dataCriacao(w.getDataCriacao()).build()).collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public Favorito buscarFavorito(String id){
        var optional = favoritoRepository.findById(id);

        if(optional.isPresent()){
            return Favorito.builder().id(optional.get().getId())
                    .idCliente(optional.get().getIdCliente())
                    .idProduto(optional.get().getIdProduto())
                    .dataCriacao(optional.get().getDataCriacao()).build();
        }

        throw new FavoritoNotFoundException("Cliente ou Produto não encontrado na lista de favoritos");
    }

    @Override
    public Long consultarFavoritoTotalPorCliente(String idCliente) {

        return favoritoRepository.countByIdCliente(idCliente);
    }

    @Override
    public void salvar(Favorito favorito) {
        favoritoRepository.save(getEntity(favorito));
    }


    @Override
    public void removerFavorito(Favorito favorito) {
        favoritoRepository.delete(getEntity(favorito));
    }

    private FavoritoEntity getEntity(Favorito favorito){
        return new FavoritoEntity(favorito.getId(), favorito.getIdCliente(),
                favorito.getIdProduto(), favorito.getDataCriacao());
    }
}
