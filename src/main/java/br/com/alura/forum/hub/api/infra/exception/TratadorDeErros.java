package br.com.alura.forum.hub.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErros {

    // 404 - Recurso não encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagemErro> tratarErro404(EntityNotFoundException ex) {
        var erro = new MensagemErro("Recurso não encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 400 - Erros de validação em @Valid (campos do corpo da requisição)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErro> tratarErroValidacao(MethodArgumentNotValidException ex) {
        var detalhes = ex.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        var erro = new MensagemErro("Erro de validação", detalhes);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 400 - Violação de constraints fora do corpo (ex: parâmetros de path/query)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MensagemErro> tratarViolacaoConstraint(ConstraintViolationException ex) {
        var detalhes = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        var erro = new MensagemErro("Violação de restrições", detalhes);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 500 - Erros genéricos não tratados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErro> tratarErroGeral(Exception ex) {
        var erro = new MensagemErro("Erro interno", "Ocorreu um erro inesperado.");
        ex.printStackTrace(); // Opcional: log para facilitar o debug
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // Classe interna para formato de resposta
    public record MensagemErro(String erro, String detalhe) {}

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemErro> tratarViolacaoIntegridade(DataIntegrityViolationException ex) {
        var erro = new MensagemErro("Violação de integridade", "Operação não permitida. Verifique as dependências do recurso.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    public record MensagemErroTimestamp(
            String erro,
            String detalhe,
            int status,
            String timestamp
    ) {
        public MensagemErroTimestamp(String erro, String detalhe, HttpStatus status) {
            this(erro, detalhe, status.value(), java.time.OffsetDateTime.now().toString());
        }
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<MensagemErro> tratarValidacaoException(ValidacaoException ex) {
        var erro = new MensagemErro("Erro de validação", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
