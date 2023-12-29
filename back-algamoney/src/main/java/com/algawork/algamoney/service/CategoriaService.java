package com.algawork.algamoney.service;

import com.algawork.algamoney.model.Categoria;
import com.algawork.algamoney.model.Pessoa;
import com.algawork.algamoney.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria atualizar(Categoria categoria, Long id) {
        Categoria categoriaSalva = buscarPorId(id);
        BeanUtils.copyProperties(categoria, categoriaSalva, "id");

        return repository.save(categoriaSalva);

    }
    private Categoria buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
