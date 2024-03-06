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

import com.generation.identificai.model.Categorias;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.model.UsuarioAdm;
import com.generation.identificai.service.UsuarioAdmService;

@RestController
@RequestMapping("/usuarios-adm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioAdmController {

    @Autowired
    private UsuarioAdmService usuarioAdmService;
    


    public UsuarioAdmController() {
	
	}

	@PostMapping("/cadastrar")
    public ResponseEntity<UsuarioAdm> cadastrarUsuarioAdm(@RequestBody UsuarioAdm usuarioAdm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAdmService.cadastrarUsuarioAdm(usuarioAdm));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioAdm> loginUsuarioAdm(@RequestBody UsuarioAdm usuarioAdm) {
        return ResponseEntity.ok(usuarioAdmService.loginUsuarioAdm(usuarioAdm));
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categorias> criarCategoria(long l, @RequestBody Categorias categorias) {
        UsuarioAdm usuarioAdm = usuarioAdmService.getLoggedUser(); // Obter o usuário logado
        Categorias novaCategoria = usuarioAdmService.criarCategoria(usuarioAdm.getId(), categorias);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categorias> atualizarCategoria(@PathVariable Long id, @RequestBody Categorias categoria) {
        return ResponseEntity.ok(usuarioAdmService.atualizarCategoria(id, categoria));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        usuarioAdmService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/servicos")
    public ResponseEntity<Servicos> criarServico(@RequestBody Servicos servico) {
        UsuarioAdm usuarioAdm = usuarioAdmService.getLoggedUser(); // Obter o usuário logado
        Servicos novoServico = usuarioAdmService.criarServicos(usuarioAdm.getId(), servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }

    @PutMapping("/servicos/{id}")
    public ResponseEntity<Servicos> atualizarServico(@PathVariable Long id, @RequestBody Servicos servicos) {
        return ResponseEntity.ok(usuarioAdmService.atualizarServico(id, servicos));
    }

    @DeleteMapping("/servicos/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        usuarioAdmService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Servicos>> listarServicos() {
        return ResponseEntity.ok(usuarioAdmService.listarServicos());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categorias>> listarCategorias() {
        return ResponseEntity.ok(usuarioAdmService.listarCategorias());
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAdm>> listarUsuarios() {
        return ResponseEntity.ok(usuarioAdmService.listarUsuarios());
    }

	public void setUsuarioAdmService(UsuarioAdmService usuarioAdmService2) {
		
		
	}
}
