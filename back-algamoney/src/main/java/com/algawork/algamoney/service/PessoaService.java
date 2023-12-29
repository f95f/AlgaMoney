package com.algawork.algamoney.service;

import com.algawork.algamoney.model.Pessoa;
import com.algawork.algamoney.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repository;
    public Pessoa atualizarPessoa(Pessoa pessoa, Long id){

        Pessoa pessoaSalva = buscarPorId(id);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
        return repository.save(pessoaSalva);

    }

    public Pessoa atualizarStatus(Boolean ativo, Long id) {

        Pessoa pessoaSalva = buscarPorId(id);
        pessoaSalva.setAtivo(ativo);
        return repository.save(pessoaSalva);
    }

    private Pessoa buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
