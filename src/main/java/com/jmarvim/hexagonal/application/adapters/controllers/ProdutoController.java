package com.jmarvim.hexagonal.application.adapters.controllers;

import com.jmarvim.hexagonal.domain.dto.EstoqueDTO;
import com.jmarvim.hexagonal.domain.dto.ProdutoDTO;
import com.jmarvim.hexagonal.domain.ports.interfaces.ProdutoServicePort;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoServicePort produtoServicePort;

    public ProdutoController(ProdutoServicePort produtoServicePort) {
        this.produtoServicePort = produtoServicePort;
    }

    @PostMapping
    ProdutoDTO criarProdutos(@RequestBody ProdutoDTO produtoDTO) {
        return produtoServicePort.criarProduto(produtoDTO);
    }

    @GetMapping
    List<ProdutoDTO> getProdutos() {
        return produtoServicePort.buscarProdutos();
    }

    @PutMapping(value = "/{sku}")
    void atualizarEstoque(@PathVariable String sku, @RequestBody EstoqueDTO estoqueDTO) throws ChangeSetPersister.NotFoundException {
        produtoServicePort.atualizarEstoque(sku, estoqueDTO);
    }

    @DeleteMapping
    void deletarTodos(){
        produtoServicePort.deletarTodos();
    }

    @GetMapping(value = "/{sku}")
    ProdutoDTO buscarProdutoPorSku(@PathVariable String sku) {
        return produtoServicePort.buscarProdutoPorSku(sku);
    }
}
