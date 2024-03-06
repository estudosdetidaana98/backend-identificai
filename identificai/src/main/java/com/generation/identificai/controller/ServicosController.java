package com.generation.identificai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.identificai.model.Servicos;
import com.generation.identificai.service.ServicosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServicosController {

    @Autowired
    private ServicosService servicosService;

    @PostMapping
    public ResponseEntity<Servicos> criarServico(@Valid @RequestBody Servicos servico) {
        Servicos servicoCriado = servicosService.criarServico(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicos> atualizarServico(@PathVariable Long id, @Valid @RequestBody Servicos servico) {
        return servicosService.atualizarServico(id, servico)
                .map(servicoAtualizado -> ResponseEntity.ok(servicoAtualizado))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        if (servicosService.deletarServico(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Servicos>> listarTodosServicos() {
        List<Servicos> servicos = servicosService.listarServicos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/gratuitos")
    public ResponseEntity<List<Servicos>> listarServicosGratuitos() {
        List<Servicos> servicosGratuitos = servicosService.listarServicosGratuitos();
        return ResponseEntity.ok(servicosGratuitos);
    }

    @GetMapping("/pagos")
    public ResponseEntity<List<Servicos>> listarServicosPagos() {
        List<Servicos> servicosPagos = servicosService.listarServicosPagos();
        return ResponseEntity.ok(servicosPagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicos> encontrarServicoPorId(@PathVariable Long id) {
        return servicosService.encontrarPorId(id)
                .map(servico -> ResponseEntity.ok(servico))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

