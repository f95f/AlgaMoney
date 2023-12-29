package com.algawork.algamoney.resource;

import com.algawork.algamoney.exceptionhandler.AlgamoneyExceptionHandler;
import com.algawork.algamoney.model.Categoria;
import com.algawork.algamoney.model.Lancamento;
import com.algawork.algamoney.repository.LancamentoRepository;
import com.algawork.algamoney.repository.filter.LancamentoFilter;
import com.algawork.algamoney.service.LancamentoService;
import com.algawork.algamoney.service.exception.PessoaNaoDisponivelException;
import jakarta.validation.Valid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository repository;
    @Autowired
    private LancamentoService service;
    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Lancamento> pesquisar(LancamentoFilter filter){

        List<Lancamento> lancamentos = repository.findAll();
        //return !lancamentos.isEmpty()? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
        return repository.filtrar(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        Optional<Lancamento> lancamentoEncontrado = repository.findById(id);
        return lancamentoEncontrado.isEmpty()? ResponseEntity.notFound().build() : ResponseEntity.ok(lancamentoEncontrado.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento lancamento){

        Lancamento lancamentoCriado = service.save(lancamento);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(lancamentoCriado.getId())
                .toUri();
        return ResponseEntity.created(uri).body(lancamentoCriado);

    }

    @ExceptionHandler({ PessoaNaoDisponivelException.class })
    public ResponseEntity<Object> handlePessoaNaoDisponivelException( PessoaNaoDisponivelException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.indisponivel", null, LocaleContextHolder.getLocale());
        String mensagemDeveloper = ex.toString();

        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDeveloper));
        return ResponseEntity.badRequest().body(erros);
    }

}
