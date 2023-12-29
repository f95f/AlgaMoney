package com.algawork.algamoney.service;

import com.algawork.algamoney.model.Lancamento;
import com.algawork.algamoney.model.Pessoa;
import com.algawork.algamoney.repository.LancamentoRepository;
import com.algawork.algamoney.repository.PessoaRepository;
import com.algawork.algamoney.service.exception.PessoaNaoDisponivelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {
    @Autowired
    private LancamentoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    public Lancamento save(Lancamento lancamento) {

        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
        if(pessoa.isEmpty() || pessoa.get().isInativo()){
            throw new PessoaNaoDisponivelException();
        }

        return repository.save(lancamento);
    }
}
