package com.generation.identificai.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
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
import com.generation.identificai.model.Servicos;
import com.generation.identificai.service.ServicosService;

public class ServicosControllerTest {

    @Mock
    private ServicosService servicosServiceMock;

    @InjectMocks
    private ServicosController servicosController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarServico() {
        Servicos servico = new Servicos();
        servico.setId(1L);
        servico.setNome("Servico 1");
        servico.setDescricao("Descrição 1");
        servico.setPreco(BigDecimal.TEN);

        when(servicosServiceMock.criarServico(any(Servicos.class))).thenReturn(servico);

        ResponseEntity<Servicos> responseEntity = servicosController.criarServico(servico);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(servico, responseEntity.getBody());
    }

    @Test
    public void testAtualizarServico() {
        Servicos servicoExistente = new Servicos();
        servicoExistente.setId(1L);
        servicoExistente.setNome("Servico 1");
        servicoExistente.setDescricao("Descrição 1");
        servicoExistente.setPreco(BigDecimal.TEN);

        Servicos servicoAtualizado = new Servicos();
        servicoAtualizado.setId(1L);
        servicoAtualizado.setNome("Servico Atualizado");
        servicoAtualizado.setDescricao("Descrição Atualizada");
        servicoAtualizado.setPreco(BigDecimal.valueOf(20));

        when(servicosServiceMock.atualizarServico(1L, servicoAtualizado)).thenReturn(Optional.of(servicoAtualizado));

        ResponseEntity<Servicos> responseEntity = servicosController.atualizarServico(1L, servicoAtualizado);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servicoAtualizado, responseEntity.getBody());
    }

    @Test
    public void testAtualizarServicoNotFound() {
        Servicos servicoAtualizado = new Servicos();
        servicoAtualizado.setId(1L);

        when(servicosServiceMock.atualizarServico(1L, servicoAtualizado)).thenReturn(Optional.empty());

        ResponseEntity<Servicos> responseEntity = servicosController.atualizarServico(1L, servicoAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletarServico() {
        when(servicosServiceMock.deletarServico(1L)).thenReturn(true);

        ResponseEntity<Void> responseEntity = servicosController.deletarServico(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletarServicoNotFound() {
        when(servicosServiceMock.deletarServico(1L)).thenReturn(false);

        ResponseEntity<Void> responseEntity = servicosController.deletarServico(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testListarTodosServicos() {
        List<Servicos> servicosList = new ArrayList<>();
        servicosList.add(new Servicos());
        servicosList.add(new Servicos());

        when(servicosServiceMock.listarServicos()).thenReturn(servicosList);

        ResponseEntity<List<Servicos>> responseEntity = servicosController.listarTodosServicos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servicosList, responseEntity.getBody());
    }

    @Test
    public void testListarServicosGratuitos() {
        List<Servicos> servicosGratuitos = new ArrayList<>();
        servicosGratuitos.add(new Servicos());

        when(servicosServiceMock.listarServicosGratuitos()).thenReturn(servicosGratuitos);

        ResponseEntity<List<Servicos>> responseEntity = servicosController.listarServicosGratuitos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servicosGratuitos, responseEntity.getBody());
    }

    @Test
    public void testListarServicosPagos() {
        List<Servicos> servicosPagos = new ArrayList<>();
        servicosPagos.add(new Servicos());

        when(servicosServiceMock.listarServicosPagos()).thenReturn(servicosPagos);

        ResponseEntity<List<Servicos>> responseEntity = servicosController.listarServicosPagos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servicosPagos, responseEntity.getBody());
    }

    @Test
    public void testEncontrarServicoPorId() {
        Servicos servico = new Servicos();
        servico.setId(1L);

        when(servicosServiceMock.encontrarPorId(1L)).thenReturn(Optional.of(servico));

        ResponseEntity<Servicos> responseEntity = servicosController.encontrarServicoPorId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(servico, responseEntity.getBody());
    }

    @Test
    public void testEncontrarServicoPorIdNotFound() {
        when(servicosServiceMock.encontrarPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Servicos> responseEntity = servicosController.encontrarServicoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

