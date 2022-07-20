package com.jmarvim.hexagonal.domain.ports.repositories;

import com.jmarvim.hexagonal.domain.Produto;

import java.util.List;

public interface ProdutoRepositoryPort {
    List<Produto> buscarTodos();

    Produto buscarPeloSku(String sku);

    Produto salvar(Produto produto);

    void deletarTodos();
}