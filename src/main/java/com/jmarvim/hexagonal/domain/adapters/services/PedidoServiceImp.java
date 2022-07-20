package com.jmarvim.hexagonal.domain.adapters.services;

import com.jmarvim.hexagonal.domain.Produto;
import com.jmarvim.hexagonal.domain.dto.EstoqueDTO;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import com.jmarvim.hexagonal.domain.ports.interfaces.ProdutoServicePort;
import com.jmarvim.hexagonal.domain.ports.repositories.ProdutoRepositoryPort;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PedidoServiceImp implements ProdutoServicePort {

    private final ProdutoRepositoryPort produtoRepository;

    public PedidoServiceImp(ProdutoRepositoryPort produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(produtoDTO);
        return this.produtoRepository.salvar(produto).toProdutoDTO();
    }

    @Override
    public List<ProdutoDTO> buscarProdutos() {
        List<Produto> produtos = this.produtoRepository.buscarTodos();
        List<ProdutoDTO> produtoDTOS = produtos.stream().map(Produto::toProdutoDTO).collect(Collectors.toList());
        return produtoDTOS;
    }

    @Override
    public void atualizarEstoque(String sku, EstoqueDTO estoqueDTO) throws ChangeSetPersister.NotFoundException {
        Produto produto = this.produtoRepository.buscarPeloSku(sku);

        if (Objects.isNull(produto))
            throw new ChangeSetPersister.NotFoundException();

        produto.atualizarEstoque(estoqueDTO.getQuantidade());

        this.produtoRepository.salvar(produto);
    }

    @Override
    public void deletarTodos() {
        this.produtoRepository.deletarTodos();
    }

    @Override
    public ProdutoDTO buscarProdutoPorSku(String sku){
        return produtoRepository.buscarPeloSku(sku).toProdutoDTO();
    }
}