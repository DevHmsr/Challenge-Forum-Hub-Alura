package br.com.alura.forum.hub.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoUsuario(
        @NotBlank String login,
        @NotBlank String password
) {
}
