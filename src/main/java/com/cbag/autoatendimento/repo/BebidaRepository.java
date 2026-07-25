package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Bebida;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    @Query("select b from Bebida b where b.quantidadeEmEstoque>0")
    List<Bebida> recuperarWhereEstoqueMaiorQueZero();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Bebida b where b.codigo=:codigo")
    Optional<Bebida> recuperarPorCodigoETravar(@Param("codigo") Long codigo); // usado para quando formos consultar antes de alterar
}
