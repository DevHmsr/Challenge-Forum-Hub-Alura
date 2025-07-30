package br.com.alura.forum.hub.api.infra.security;

import br.com.alura.forum.hub.api.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var requestPath = request.getRequestURI();
        var isLoginRequest = requestPath.equals("/login");

        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.replace("Bearer ", "");

            try {
                var username = tokenService.getSubject(token);
                if (username != null) {
                    var usuario = usuarioRepository.findByLogin(username)
                            .orElseThrow(() -> new RuntimeException("Usuário do token não encontrado"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"erro\": \"Token invalido ou expirado.\"}");
                return;
            }
        }

        if (!isLoginRequest && SecurityContextHolder.getContext().getAuthentication() == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

            var json = """
                    {
                      "timestamp": "%s",
                      "status": 403,
                      "erro": "Forbidden",
                      "mensagem": "Acesso negado",
                      "detalhe": "Voce nao tem permissao para acessar este recurso.",
                      "path": "%s"
                    }
                    """.formatted(OffsetDateTime.now(), request.getRequestURI());

            response.getWriter().write(json);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
