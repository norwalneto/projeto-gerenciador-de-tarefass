package com.projetos.gerenciadordetarefas.controller;

import com.projetos.gerenciadordetarefas.model.Usuario;
import com.projetos.gerenciadordetarefas.service.UsuarioService;
import com.projetos.gerenciadordetarefas.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Endpoint para registrar um novo usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }

    // Endpoint para login do usuario (a ser implementado com  JWT)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario){
        // Autenticação e geração do JWT serão implementadas em breve
        try{
            // Autenticar usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));

            // Gerar token JWT
            String token = jwtUtil.gerarToken(usuario.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciais invalidas");
        }
    }
}
