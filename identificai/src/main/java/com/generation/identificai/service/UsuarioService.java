package com.generation.identificai.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.generation.identificai.model.UsuarioLogin;
import com.generation.identificai.model.Usuarios;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.repository.UsuarioRepository;
import com.generation.identificai.repository.ServicosRepository;
import com.generation.identificai.security.JwtService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuarios cadastrarUsuario(Usuarios usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!");

        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuarios atualizarUsuario(Usuarios usuario) {
        if (!usuarioRepository.existsById(usuario.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");

        Optional<Usuarios> buscaUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (buscaUsuario.isPresent() && !buscaUsuario.get().getId().equals(usuario.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já utilizado por outro usuário!");

        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioLogin autenticarUsuario(UsuarioLogin usuarioLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioLogin.getEmail(), usuarioLogin.getSenha()));
        if (authentication.isAuthenticated()) {
            String email = usuarioLogin.getEmail();
            Usuarios usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado!"));
            String token = jwtService.generateToken(email);
            usuarioLogin.setId(usuario.getId());
            usuarioLogin.setNome(usuario.getNome());
            usuarioLogin.setFoto(usuario.getFoto());
            usuarioLogin.setToken(token);
            usuarioLogin.setSenha("");
            return usuarioLogin;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!");
        }
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");

        usuarioRepository.deleteById(id);
    }

    public List<Servicos> visualizarServicosDisponiveis() {
        return servicosRepository.findAll();
    }

    public void adquirirServico(Long idUsuario, Long idServico) {
        Optional<Usuarios> usuario = usuarioRepository.findById(idUsuario);
        Optional<Servicos> servico = servicosRepository.findById(idServico);

        if (usuario.isPresent() && servico.isPresent()) {
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou serviço não encontrado!");
        }
    }

    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }
}
