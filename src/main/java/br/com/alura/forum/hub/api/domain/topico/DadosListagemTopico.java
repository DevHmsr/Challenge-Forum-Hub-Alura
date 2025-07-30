package br.com.alura.forum.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String curso,
        String nomeAutor,
        String mensagem,
        LocalDateTime dataCriacao
) {

    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getCurso().getNome(),
                topico.getAutor().getNome(),
                topico.getMensagem(),
                topico.getDataCriacao()
        );
    }
}
