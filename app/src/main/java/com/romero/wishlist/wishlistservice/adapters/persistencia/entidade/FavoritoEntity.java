package com.romero.wishlist.wishlistservice.adapters.persistencia.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "favoritos")
public class FavoritoEntity {

    @Id
    private String id;
    @Indexed(name = "index_id_cliente")
    @Field("id_cliente")
    private String idCliente;
    @Indexed(name = "index_id_produto")
    @Field("id_produto")
    private String idProduto;
    @Field("data_criacao")
    private LocalDateTime dataCriacao;
}
