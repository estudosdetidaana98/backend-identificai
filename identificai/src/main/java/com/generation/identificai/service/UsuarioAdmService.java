package com.generation.identificai.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.identificai.model.Categorias;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.model.UsuarioAdm;
import com.generation.identificai.repository.CategoriasRepository;
import com.generation.identificai.repository.ServicosRepository;
import com.generation.identificai.repository.UsuarioAdmRepository;
import com.generation.identificai.security.SecurityUtils;

@Service
public class UsuarioAdmService {

    @Autowired
    private UsuarioAdmRepository usuarioAdmRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioAdm cadastrarUsuarioAdm(UsuarioAdm usuarioAdm) {
        usuarioAdm.setSenha(passwordEncoder.encode(usuarioAdm.getSenha()));
        UsuarioAdm novoUsuario = usuarioAdmRepository.save(usuarioAdm);
        novoUsuario.setSenha(null); // Limpa a senha do usuário antes de retornar
        return novoUsuario;
    }

    public UsuarioAdm loginUsuarioAdm(UsuarioAdm usuarioAdm) {
        Optional<UsuarioAdm> usuarioAdmOptional = usuarioAdmRepository.findByEmail(usuarioAdm.getEmail());
        if (usuarioAdmOptional.isPresent()) {
            UsuarioAdm usuarioAdmDB = usuarioAdmOptional.get();
            if (passwordEncoder.matches(usuarioAdm.getSenha(), usuarioAdmDB.getSenha())) {
                return usuarioAdmDB;
            }
        }
        return null;
    }

    public UsuarioAdm getLoggedUser() {
        String loggedUserEmail = SecurityUtils.getLoggedUserEmail();
        if (loggedUserEmail != null) {
            Optional<UsuarioAdm> usuarioAdmOptional = usuarioAdmRepository.findByEmail(loggedUserEmail);
            return usuarioAdmOptional.orElse(null);
        }
        return null;
    }

    public Optional<UsuarioAdm> findByEmail(String email) {
        return usuarioAdmRepository.findByEmail(email);
    }

    public Categorias criarCategoria(Long idAdm, Categorias categoria) {
        categoria.setIdAdm(idAdm);
        return categoriasRepository.save(categoria);
    }

    public Categorias atualizarCategoria(Long id, Categorias categoria) {
        Optional<Categorias> categoriaOptional = categoriasRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            categoria.setId(id);
            return categoriasRepository.save(categoria);
        }
        return null;
    }

    public void deletarCategoria(Long id) {
        categoriasRepository.deleteById(id);
    }

    public Servicos criarServicos(Long idAdm, Servicos servicos) {
        servicos.setIdAdm(idAdm);
        Servicos novoServico = servicosRepository.save(servicos);
        novoServico.setId(null); // Remove o ID gerado automaticamente
        return novoServico;
    }

    public Servicos atualizarServico(Long id, Servicos servicos) {
        Optional<Servicos> servicoOptional = servicosRepository.findById(id);
        if (servicoOptional.isPresent()) {
            servicos.setId(id);
            return servicosRepository.save(servicos);
        }
        return null;
    }

    public void deletarServico(Long id) {
        servicosRepository.deleteById(id);
    }

    public List<Servicos> listarServicos() {
        return servicosRepository.findAll();
    }

    public List<Categorias> listarCategorias() {
        return categoriasRepository.findAll();
    }

    public List<UsuarioAdm> listarUsuarios() {
        return usuarioAdmRepository.findAll();
    }

    public List<Servicos> listarServicosGratuitosPorUsuario(Long usuarioId) {
        return servicosRepository.findByUsuarioIdAndPrecoEquals(usuarioId, BigDecimal.ZERO);
    }

    public List<Servicos> listarServicosPagosPorUsuario(Long usuarioId) {
        return servicosRepository.findByUsuarioIdAndPrecoGreaterThan(usuarioId, BigDecimal.ZERO);
    }

    public List<Servicos> listarTodosServicosPorUsuario(Long usuarioId) {
        return servicosRepository.findByUsuarioId(usuarioId);
    }

}
