package com.romero.wishlist.wishlistservice.adapters.persistencia.repository;

import com.romero.wishlist.wishlistservice.adapters.persistencia.entidade.FavoritoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;


public interface FavoritoRepository extends MongoRepository<FavoritoEntity, String> {

    @Query("{'idCliente' : ?0 }")
    List<FavoritoEntity> findByIdCliente(String idCliente);

    @Query(value = "{idCliente: ?0}", count = true)
    Long countByIdCliente(String idCliente);
}
