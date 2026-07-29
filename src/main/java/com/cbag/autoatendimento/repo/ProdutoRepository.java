package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Produto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByTipoProdutoId(Long tipoProdutoId);

    Optional<Produto> findByCodBarras(Long codBarras);

    boolean existsByTipoProdutoId(Long tipoProdutoId);

    @Query("select p from Produto p where p.tipoProduto.controlaEstoque = false or p.quantidadeEmEstoque > 0")
    List<Produto> recuperarDisponiveis();

    @Query("select p from Produto p where p.tipoProduto.controlaEstoque = true and p.quantidadeEmEstoque > 0")
    List<Produto> recuperarWhereEstoqueMaiorQueZero();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Produto p where p.codigo=:codigo")
    Optional<Produto> recuperarPorCodigoETravar(@Param("codigo") Long codigo); // usado para quando formos consultar antes de alterar?
}
