package com.generation.identificai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.identificai.model.Categorias;
import com.generation.identificai.model.Servicos;
import com.generation.identificai.model.UsuarioAdm;
import com.generation.identificai.service.UsuarioAdmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioAdmController.class)
public class UsuarioAdmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioAdmService usuarioAdmService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Configuração inicial, se necessário.
    }

    @Test
    public void cadastrarUsuarioAdm_ShouldReturnUsuarioAdm() throws Exception {
        UsuarioAdm usuarioAdm = new UsuarioAdm(1L, "email@test.com", "senha123", "foto.jpg", "Nome Usuario", "ADMIN");
        given(usuarioAdmService.cadastrarUsuarioAdm(any(UsuarioAdm.class))).willReturn(usuarioAdm);

        mockMvc.perform(post("/usuarios-adm/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioAdm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    public void loginUsuarioAdm_ShouldReturnUsuarioAdm() throws Exception {
        UsuarioAdm usuarioAdm = new UsuarioAdm(1L, "email@test.com", "senha123", "foto.jpg", "Nome Usuario", "ADMIN");
        given(usuarioAdmService.loginUsuarioAdm(any(UsuarioAdm.class))).willReturn(usuarioAdm);

        mockMvc.perform(post("/usuarios-adm/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioAdm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    public void listarCategorias_ShouldReturnCategoriasList() throws Exception {
        List<Categorias> categoriasList = new ArrayList<>();
        categoriasList.add(new Categorias(1L, "Categoria 1", "Descrição 1", null));
        categoriasList.add(new Categorias(2L, "Categoria 2", "Descrição 2", null));

        given(usuarioAdmService.listarCategorias()).willReturn(categoriasList);

        mockMvc.perform(get("/usuarios-adm/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categoriasList.size()));
    }

    @Test
    public void listarServicos_ShouldReturnServicosList() throws Exception {
        List<Servicos> servicosList = new ArrayList<>();
        servicosList.add(new Servicos());
        servicosList.add(new Servicos());

        given(usuarioAdmService.listarServicos()).willReturn(servicosList);

        mockMvc.perform(get("/usuarios-adm/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(servicosList.size()));
    }

}