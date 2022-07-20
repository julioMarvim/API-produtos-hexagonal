package com.jmarvim.hexagonal.application.adapters.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmarvim.hexagonal.domain.adapters.services.PedidoServiceImp;
import com.jmarvim.hexagonal.domain.dto.EstoqueDTO;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PedidoServiceImp service;

    @Test
    void criarProdutos() throws Exception {
        var produtoDto = new ProdutoDTO("1", "Guitarra", 2000.00, 1.0);
        when(service.criarProduto(any())).thenReturn(produtoDto);
        var result = mockMvc.perform(
                MockMvcRequestBuilders.post("/produtos")
                    .content(asJsonString(produtoDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ProdutoDTO produtoDTO = new ObjectMapper().readValue(json, ProdutoDTO.class);

        assertThat(produtoDTO.getNome()).isEqualTo("Guitarra");
    }

    @Test
    void getProdutos() throws Exception {
        var produtos = Arrays.asList(
            new ProdutoDTO("1", "Guitarra", 2000.00, 1.0),
            new ProdutoDTO("2", "Pedaleira", 5000.00, 5.0),
            new ProdutoDTO("3", "Gaita", 1000.00, 10.0)
        );

        when(service.buscarProdutos()).thenReturn(produtos);

        var result = mockMvc.perform(
                MockMvcRequestBuilders.get("/produtos"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<ProdutoDTO> listProdutos = mapper.readValue(json, new TypeReference<List<ProdutoDTO>>() {});

        assertThat(listProdutos.get(0).getNome()).isEqualTo("Guitarra");
        assertThat(listProdutos.get(1).getNome()).isEqualTo("Pedaleira");
        assertThat(listProdutos.get(2).getNome()).isEqualTo("Gaita");
    }

    @Test
    void atualizarEstoque() throws Exception {
        var sku = "1";
        var estoqueDto = new EstoqueDTO();

        doNothing().when(service).atualizarEstoque(sku, estoqueDto);

        var result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/produtos/{sku}", "sku", sku)
                                .content(asJsonString(estoqueDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}