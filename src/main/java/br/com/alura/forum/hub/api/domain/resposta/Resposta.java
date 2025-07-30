package br.com.alura.forum.hub.api.domain.resposta;

import br.com.alura.forum.hub.api.domain.topico.Topico;
import br.com.alura.forum.hub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private boolean solucao = false;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "topico_id")
    private Topico topico;
}
