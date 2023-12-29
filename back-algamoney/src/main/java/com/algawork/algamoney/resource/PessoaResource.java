package com.algawork.algamoney.resource;

import com.algawork.algamoney.model.Pessoa;
import com.algawork.algamoney.repository.PessoaRepository;
import com.algawork.algamoney.service.PessoaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository repository;
    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<?> listar(){
        List<Pessoa> pessoas = repository.findAll();
        return !pessoas.isEmpty()?
                ResponseEntity.ok(pessoas) :
                ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id){
        Optional<Pessoa> pessoaEncontrada = repository.findById(id);
        return pessoaEncontrada.isPresent()?
                ResponseEntity.ok(pessoaEncontrada.get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaSalva = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pessoaSalva.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id, HttpServletResponse response){

        Pessoa pessoaSalva = service.atualizarPessoa(pessoa, id);

        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarStatus(@Valid @RequestBody Boolean ativo, @PathVariable Long id, HttpServletResponse response){
        service.atualizarStatus(ativo, id);
    }
}
