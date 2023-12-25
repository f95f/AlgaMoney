package com.algawork.algamoney.resource;

import com.algawork.algamoney.model.Categoria;
import com.algawork.algamoney.repository.CategoriaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository repository;

    @GetMapping
    public ResponseEntity<?> listar(){
        List<Categoria> categorias = repository.findAll();
        return !categorias.isEmpty()? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();

    }
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED) <- não é necessária quando returno não é void;
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = repository.save(categoria);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(categoriaSalva.getId())
                    .toUri();
        //response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id){
        return repository.findById(id).get();
    }

}




































