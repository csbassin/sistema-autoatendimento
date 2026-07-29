package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.ItemPedido;
import com.cbag.autoatendimento.model.idClasses.ItemPedidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoId> {
    boolean existsByProdutoCodigo(Long codigo);
}
