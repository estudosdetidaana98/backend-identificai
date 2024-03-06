package com.generation.identificai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.generation.identificai.model.UsuarioLogin;
import com.generation.identificai.model.Usuarios;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody UsuarioLogin usuarioLogin) {
        return ResponseEntity.ok(usuarioService.autenticarUsuario(usuarioLogin));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuarios> postUsuario(@RequestBody @Valid Usuarios usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuarios> putUsuario(@PathVariable Long id, @Valid @RequestBody Usuarios usuario) {
        if (!usuario.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuario));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Servicos>> getServicos() {
        return ResponseEntity.ok(usuarioService.visualizarServicosDisponiveis());
    }

    @PostMapping("/{idUsuario}/servicos/{idServico}/adquirir")
    public ResponseEntity<Void> adquirirServico(@PathVariable Long idUsuario, @PathVariable Long idServico) {
        usuarioService.adquirirServico(idUsuario, idServico);
        return ResponseEntity.ok().build();
    }
}
