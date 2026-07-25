package com.cbag.autoatendimento.repo;

import com.cbag.autoatendimento.model.Salgado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalgadoRepository extends JpaRepository<Salgado, Long> {
}
