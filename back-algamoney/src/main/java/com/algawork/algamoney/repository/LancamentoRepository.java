package com.algawork.algamoney.repository;

import com.algawork.algamoney.model.Lancamento;
import com.algawork.algamoney.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
}
