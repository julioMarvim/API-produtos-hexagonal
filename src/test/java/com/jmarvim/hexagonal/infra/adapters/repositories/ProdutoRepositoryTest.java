package com.jmarvim.hexagonal.infra.adapters.repositories;

import com.jmarvim.hexagonal.domain.Produto;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repository;

    @BeforeEach
    void setUp() {
        List<Produto> produtos = Arrays.asList(
                new Produto(new ProdutoDTO("1", "Guitarra", 2.0, 2000.0)),
                new Produto(new ProdutoDTO("2", "Pedaleira", 3.0, 5000.0)),
                new Produto(new ProdutoDTO("3", "Gaita", 2.0, 1000.0))
        );

        produtos.forEach(
                produto -> {
                    repository.salvar(produto);
                }
        );
    }

    @AfterEach
    public void destroyAll() {
        repository.deletarTodos();
    }

    @Test
    void buscarTodos() {
        var result = repository.buscarTodos();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getNome()).isEqualTo("Guitarra");
        assertThat(result.get(1).getNome()).isEqualTo("Pedaleira");
        assertThat(result.get(2).getNome()).isEqualTo("Gaita");
    }


    @Test
    void buscarPeloSku() {
        var result = repository.buscarPeloSku("1");
        assertThat(result.getNome()).isEqualTo("Guitarra");
    }

    @Test
    void salvar() {
        var produto = new Produto(new ProdutoDTO("1", "Bateria", 2.0, 2000.0));
        var result = repository.salvar(produto);

        assertThat(result.getNome()).isEqualTo("Bateria");
    }
}