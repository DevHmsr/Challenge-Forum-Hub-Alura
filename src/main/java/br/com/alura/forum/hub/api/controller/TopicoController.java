package br.com.alura.forum.hub.api.controller;

import br.com.alura.forum.hub.api.domain.curso.CursoRepository;
import br.com.alura.forum.hub.api.domain.topico.*;
import br.com.alura.forum.hub.api.domain.usuario.Usuario;
import br.com.alura.forum.hub.api.infra.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        // Verificar duplicidade
        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Tópico duplicado com mesmo título e mensagem já existe.");
        }

        var curso = cursoRepository.findByNome(dados.nomeCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        var topico = new Topico(dados.titulo(), dados.mensagem(), usuarioLogado, curso);
        repository.save(topico);

        return ResponseEntity.created(URI.create("/topicos/" + topico.getId()))
                .body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTopico>> listar() {
        var topicos = repository.findAll().stream().map(DadosListagemTopico::new).toList();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id,
                                                             @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tópico não encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
