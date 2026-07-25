package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    @Query("select b from Bebida b where b.quantidadeEmEstoque>0")
    public List<Bebida> recuperarWhereEstoqueMaiorQueZero();
}
