package com.algawork.algamoney.repository;

import com.algawork.algamoney.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("SELECT l FROM Lancamento l WHERE " +
            "(:descricao IS NULL OR LOWER(l.descricao) LIKE %:descricao%) AND " +
            "(:dataInicio IS NULL OR l.dataVencimento >= :dataInicio) AND " +
            "(:dataFim IS NULL OR l.dataVencimento <= :dataFim)")
    List<Lancamento> findWithFilters(
            @Param("descricao") String descricao,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );
}
