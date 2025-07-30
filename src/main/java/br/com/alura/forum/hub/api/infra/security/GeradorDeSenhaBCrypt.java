package br.com.alura.forum.hub.api.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenhaBCrypt {
    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String senha = "123456";
        System.out.println(encoder.encode(senha));
    }
}

