package com.algawork.algamoney.repository.lancamento;

import com.algawork.algamoney.model.Lancamento;
import com.algawork.algamoney.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
