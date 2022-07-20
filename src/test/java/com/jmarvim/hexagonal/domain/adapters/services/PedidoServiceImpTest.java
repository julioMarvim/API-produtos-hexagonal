package com.jmarvim.hexagonal.domain.adapters.services;

import com.jmarvim.hexagonal.domain.Produto;
import com.jmarvim.hexagonal.domain.dto.EstoqueDTO;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import com.jmarvim.hexagonal.domain.ports.repositories.ProdutoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PedidoServiceImpTest {

    @Mock
    private ProdutoRepositoryPort repository;

    PedidoServiceImp service;

    @BeforeEach
    void setUp(){
        service = new PedidoServiceImp(repository);
    }

    @Test
    void criarProduto() {
        var produtoDto = new ProdutoDTO("1", "Guitarra", 2000.00, 1.0);
        when(repository.salvar(any())).thenReturn(new Produto(produtoDto));

        var result = service.criarProduto(produtoDto);
        assertThat(result.getNome()).isNotNull();
        assertThat(result.getNome()).isEqualTo("Guitarra");
    }

    @Test
    void buscarProdutos() {
        var produtos = Arrays.asList(
            new Produto(new ProdutoDTO("1", "Guitarra", 2000.00, 1.0)),
            new Produto(new ProdutoDTO("2", "Pedaleira", 5000.00, 5.0)),
            new Produto(new ProdutoDTO("3", "Gaita", 1000.00, 10.0))
        );

        when(repository.buscarTodos()).thenReturn(produtos);
        var result = service.buscarProdutos();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getNome()).isEqualTo("Guitarra");
        assertThat(result.get(1).getNome()).isEqualTo("Pedaleira");
        assertThat(result.get(2).getNome()).isEqualTo("Gaita");
    }

    @Test
    void atualizarEstoque() throws ChangeSetPersister.NotFoundException {
        var estoque = new EstoqueDTO();
        var sku = "1";

        var produto = new Produto(new ProdutoDTO(sku, "Guitarra", 2000.00, 3.0));

        when(repository.buscarPeloSku(sku)).thenReturn(produto);

        service.atualizarEstoque(sku, estoque);

        verify(repository, times(1)).buscarPeloSku(sku);
    }

    @Test
    void deletarTodos(){
        doNothing().when(repository).deletarTodos();

        service.deletarTodos();
        verify(repository, times(1)).deletarTodos();
    }

    @Test
    void buscarPorSku(){
        var sku = "1";

        var produto = new Produto(new ProdutoDTO(sku, "Guitarra", 2000.00, 3.0));

        when(repository.buscarPeloSku(sku)).thenReturn(produto);
        service.buscarProdutoPorSku(sku);
    }
}