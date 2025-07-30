package br.com.alura.forum.hub.api.domain.topico;

import br.com.alura.forum.hub.api.domain.resposta.RespostaDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopico(
        @NotNull Long id,
        @NotNull String titulo,
        @NotNull String mensagem,
        @NotNull LocalDateTime dataCriacao,
        @NotNull String nomeAutor,
        @NotNull TopicoStatus status,
        @NotNull List<RespostaDTO> respostas
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getNome(),
                topico.getStatus(),
                topico.getRespostas().stream()
                        .map(RespostaDTO::new)
                        .toList()
        );
    }
}
