package com.jmarvim.hexagonal.domain.ports.interfaces;

import com.jmarvim.hexagonal.domain.dto.EstoqueDTO;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface ProdutoServicePort {

    List<ProdutoDTO> buscarProdutos();

    ProdutoDTO criarProduto(ProdutoDTO produtoDTO);

    void atualizarEstoque(String sku, EstoqueDTO estoqueDTO) throws ChangeSetPersister.NotFoundException;

    void deletarTodos();

    ProdutoDTO buscarProdutoPorSku(String sku);
}