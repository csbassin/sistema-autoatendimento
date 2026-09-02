package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository<T extends Pedido> extends JpaRepository<T,Long> {
    // todo testar
    @Query("select pedido from Pedido pedido where cast(pedido.dateTime as DATE) = cast(dateTime as DATE)")
    List<Pedido> findWhereCreationDateIsEqual(LocalDateTime dateTime);
}
