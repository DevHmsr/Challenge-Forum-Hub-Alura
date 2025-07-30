package br.com.alura.forum.hub.api.controller;

import br.com.alura.forum.hub.api.domain.usuario.DadosAutenticacaoUsuario;
import br.com.alura.forum.hub.api.domain.usuario.Usuario;
import br.com.alura.forum.hub.api.infra.security.DataJWTToken;
import br.com.alura.forum.hub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> userLogin(@RequestBody @Valid @NotNull DadosAutenticacaoUsuario dados) {

        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    dados.login(), dados.password()
            );

            var authentication = authenticationManager.authenticate(authenticationToken);
            var usuario = (Usuario) authentication.getPrincipal();
            var jwt = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new DataJWTToken(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "Credenciais inválidas!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao processar a requisição."));
        }
    }
}
