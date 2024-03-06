package com.generation.identificai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.identificai.model.Categorias;
import com.generation.identificai.service.CategoriasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService; 
    
    @GetMapping
    public ResponseEntity<List<Categorias>> getAll() {
        return ResponseEntity.ok(categoriasService.listarTodasCategorias());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getById(@PathVariable Long id) {
        return categoriasService.buscarCategoriaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categorias>> getByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(categoriasService.buscarCategoriaPorDescricao(descricao));
    }
    
    @PostMapping
    public ResponseEntity<Categorias> post(@Valid @RequestBody Categorias categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriasService.salvarCategoria(categoria));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> put(@PathVariable Long id, @Valid @RequestBody Categorias categoria) {
        return categoriasService.atualizarCategoria(id, categoria)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoriasService.deletarCategoria(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
