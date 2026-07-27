package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository<T extends Pedido> extends JpaRepository<T,Long> {
}
