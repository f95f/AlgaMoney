package com.algawork.algamoney.resource;

import com.algawork.algamoney.model.Categoria;
import com.algawork.algamoney.repository.CategoriaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
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
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id){
        Optional<Categoria> categoriaEncontrada = repository.findById(id);
        return categoriaEncontrada.isPresent()? ResponseEntity.ok(categoriaEncontrada.get()) : ResponseEntity.notFound().build();
    }
    /* OUTRA OPÇÂO:
    * @GetMapping("/{codigo}")
        public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
            return this.categoriaRepository.findById(codigo)
              .map(categoria -> ResponseEntity.ok(categoria))
              .orElse(ResponseEntity.notFound().build());
        }
* */
}




































