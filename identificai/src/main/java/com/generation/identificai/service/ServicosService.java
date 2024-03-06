package com.generation.identificai.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.repository.ServicosRepository;

@Service
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    public Servicos criarServico(Servicos servico) {
        return servicosRepository.save(servico);
    }

    public Optional<Servicos> atualizarServico(Long id, Servicos servicoAtualizado) {
        return servicosRepository.findById(id).map(servicoExistente -> {
            servicoAtualizado.setId(servicoExistente.getId()); 
            return Optional.of(servicosRepository.save(servicoAtualizado));
        }).orElse(Optional.empty()); 
    }

    public boolean deletarServico(Long id) {
        if (servicosRepository.existsById(id)) {
            servicosRepository.deleteById(id);
            return true; 
        }
        return false; 
    }

    public List<Servicos> listarServicos() {
        return servicosRepository.findAll();
    }

    public List<Servicos> listarServicosGratuitos() {
        return servicosRepository.findByPreco(BigDecimal.ZERO);
    }

    public List<Servicos> listarServicosPagos() {
        return servicosRepository.findByPrecoGreaterThan(BigDecimal.ZERO);
    }

    public Optional<Servicos> encontrarPorId(Long id) {
        return servicosRepository.findById(id);
    }
}
