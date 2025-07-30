package br.com.alura.forum.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DadosCadastroTopico(
        @NotBlank @Length(min = 5) String titulo,
        @NotBlank @Length(min = 10) String mensagem,
        @NotBlank String nomeCurso
) {
}
