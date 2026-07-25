package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.idClasses.MovimentacaoEstoqueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, MovimentacaoEstoqueId> {
}
