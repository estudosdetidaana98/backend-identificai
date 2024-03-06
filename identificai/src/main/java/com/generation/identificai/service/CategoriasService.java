package com.generation.identificai.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.identificai.model.Categorias;
import com.generation.identificai.repository.CategoriasRepository;


@Service
public class CategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    public List<Categorias> listarTodasCategorias() {
        return categoriasRepository.findAll();
    }

    public Optional<Categorias> buscarCategoriaPorId(Long id) {
        return categoriasRepository.findById(id);
    }

    public List<Categorias> buscarCategoriaPorDescricao(String descricao) {
        return categoriasRepository.findAllByDescricaoContainingIgnoreCase(descricao);
    }

    public Categorias salvarCategoria(Categorias categoria) {
        return categoriasRepository.save(categoria);
    }

    public Optional<Categorias> atualizarCategoria(Long id, Categorias categoria) {
        return categoriasRepository.findById(id)
            .map(categoriaExistente -> {
                categoria.setId(id);
                return Optional.of(categoriasRepository.save(categoria));
            })
            .orElse(Optional.empty());
    }

    public boolean deletarCategoria(Long id) {
        if (categoriasRepository.existsById(id)) {
            categoriasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
