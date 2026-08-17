package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.model.VariacaoSabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariacaoSaborRepository extends JpaRepository<VariacaoSabor, Long> {
    @Query("select variacaoSabor from VariacaoSabor variacaoSabor where variacaoSabor.tipoProduto.id =: tipoProduto")
    List<VariacaoSabor> findByTipoProduto(@Param("tipoProduto")Long tipoProduto);
}
