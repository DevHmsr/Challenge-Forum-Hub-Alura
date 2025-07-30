package br.com.alura.forum.hub.api.controller;

import br.com.alura.forum.hub.api.domain.curso.Curso;
import br.com.alura.forum.hub.api.domain.curso.CursoRepository;
import br.com.alura.forum.hub.api.domain.topico.*;
import br.com.alura.forum.hub.api.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;
    private final CursoRepository cursoRepository;

    public TopicoController(TopicoRepository repository, CursoRepository cursoRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            @AuthenticationPrincipal Usuario autor) {

        Curso curso = cursoRepository.findByNome(dados.nomeCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        repository.save(topico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDTO>> listar(Pageable paginacao) {
        Page<Topico> topicos = repository.findAll(paginacao);
        return ResponseEntity.ok(TopicoDTO.converter(topicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Topico topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados) {

        Topico topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
