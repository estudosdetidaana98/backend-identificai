package com.generation.identificai.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.generation.identificai.model.UsuarioLogin;
import com.generation.identificai.model.Usuarios;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.repository.UsuarioRepository;
import com.generation.identificai.repository.ServicosRepository;
import com.generation.identificai.service.UsuarioService;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioServiceMock;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAutenticarUsuario() {
        UsuarioLogin usuarioLogin = new UsuarioLogin();
        usuarioLogin.setEmail("email");
        usuarioLogin.setSenha("senha");

        when(usuarioServiceMock.autenticarUsuario(any())).thenReturn(usuarioLogin);

        ResponseEntity<UsuarioLogin> responseEntity = usuarioController.autenticarUsuario(usuarioLogin);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuarioLogin, responseEntity.getBody());
    }

    @Test
    public void testPostUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);

        when(usuarioServiceMock.cadastrarUsuario(any())).thenReturn(usuario);

        ResponseEntity<Usuarios> responseEntity = usuarioController.postUsuario(usuario);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(usuario, responseEntity.getBody());
    }

    @Test
    public void testPutUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);

        when(usuarioServiceMock.atualizarUsuario(any())).thenReturn(usuario);

        ResponseEntity<Usuarios> responseEntity = usuarioController.putUsuario(1L, usuario);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuario, responseEntity.getBody());
    }

    @Test
    public void testDeleteUsuario() {
        ResponseEntity<Void> responseEntity = usuarioController.deleteUsuario(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetServicos() {
        List<Servicos> servicos = new ArrayList<>();

        when(usuarioServiceMock.visualizarServicosDisponiveis()).thenReturn(servicos);

        ResponseEntity<List<Servicos>> responseEntity = usuarioController.getServicos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servicos, responseEntity.getBody());
    }

    @Test
    public void testAdquirirServico() {
        ResponseEntity<Void> responseEntity = usuarioController.adquirirServico(1L, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}


