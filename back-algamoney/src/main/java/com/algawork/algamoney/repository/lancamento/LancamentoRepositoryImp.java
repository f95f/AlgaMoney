package com.algawork.algamoney.repository.lancamento;

import com.algawork.algamoney.model.Lancamento;
import com.algawork.algamoney.repository.filter.LancamentoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImp implements LancamentoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criterio = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criterio.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criterio.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criterio);
        return null;
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }
        if(!ObjectUtils.isEmpty(lancamentoFilter.getDataVencimentoInicial())){
            predicates.add(
                builder.greaterThanOrEqualTo(root.get("data_vencimento"), lancamentoFilter.getDataVencimentoInicial())
            );
        }
        if(!ObjectUtils.isEmpty(lancamentoFilter.getDataVencimentoFinal())){
            predicates.add(
                builder.lessThanOrEqualTo(root.get("data_vencimento"), lancamentoFilter.getDataVencimentoFinal())
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
