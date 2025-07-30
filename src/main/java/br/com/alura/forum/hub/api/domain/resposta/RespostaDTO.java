package br.com.alura.forum.hub.api.domain.resposta;

import java.time.LocalDateTime;

public record RespostaDTO(
        String mensagem,
        LocalDateTime dataCriacao,
        String nomeAutor
) {
    public RespostaDTO(Resposta resposta) {
        this(
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor().getNome()
        );
    }
}
