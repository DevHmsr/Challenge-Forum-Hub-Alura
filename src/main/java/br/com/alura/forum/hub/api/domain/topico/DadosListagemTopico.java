package br.com.alura.forum.hub.api.domain.topico;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String nomeCurso,
        String nomeAutor
) {

    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getCurso().getNome(),
                topico.getAutor().getNome()
        );
    }
}
