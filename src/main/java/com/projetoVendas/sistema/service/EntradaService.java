package com.projetoVendas.sistema.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projetoVendas.sistema.modelos.Entrada;
import com.projetoVendas.sistema.modelos.ItemEntrada;
import com.projetoVendas.sistema.modelos.Produtos;
import com.projetoVendas.sistema.repositorios.EntradaRepositorio;
import com.projetoVendas.sistema.repositorios.ItemEntradaRepositorio;
import com.projetoVendas.sistema.repositorios.ProdutosRepositorio;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepositorio entradaRepositorio;
    @Autowired
    private ItemEntradaRepositorio itemEntradaRepositorio;
    @Autowired
    private ProdutosRepositorio produtosRepositorio;

    // Método para adicionar um item à lista de itens da entrada
    public void adicionarItemNaLista(Long id, Entrada entrada, List<ItemEntrada> listaItemEntrada) {
        Optional<Produtos> produtos = produtosRepositorio.findById(id);
        if (produtos.isPresent()) {
            Produtos produto = produtos.get();
            boolean itemExistente = false;

            // Verifica se o produto já está na lista de itens
            for (ItemEntrada it : listaItemEntrada) {
                if (it.getProdutos().getId().equals(produto.getId())) {
                    it.setValorCusto(produto.getPrecoCusto());
                    it.setValor(produto.getPrecoVenda());
                    it.setQuantidade(it.getQuantidade() + 1); // Incrementa a quantidade
                    atualizarTotaisEntrada(entrada, it); // Atualiza os totais da entrada
                    itemExistente = true;
                    break;
                }
            }

            // Se o produto não está na lista, cria um novo item e adiciona à lista
            if (!itemExistente) {
                ItemEntrada itensOr = new ItemEntrada();
                itensOr.setProdutos(produto);
                itensOr.setValorCusto(produto.getPrecoCusto());
                itensOr.setValor(produto.getPrecoVenda());
                itensOr.setQuantidade(1.0); // Inicializa a quantidade
                listaItemEntrada.add(itensOr);
                atualizarTotaisEntrada(entrada, itensOr); // Atualiza os totais da entrada
            }
        }
    }

    // Método para atualizar os totais da entrada
    public void atualizarTotaisEntrada(Entrada entrada, ItemEntrada item) {
        entrada.setValorTotal(entrada.getValorTotal() + (item.getValor() * item.getQuantidade()));
        entrada.setQuantidadeTotal(entrada.getQuantidadeTotal() + item.getQuantidade());
    }

    // Método para salvar a entrada e seus itens no banco de dados
    public void salvarEntrada(Entrada entrada, List<ItemEntrada> listaItemEntrada) {
        entradaRepositorio.saveAndFlush(entrada); // Salva a entrada

        // Para cada item na lista, associa à entrada e salva
        for (ItemEntrada it : listaItemEntrada) {
            it.setEntrada(entrada);
            itemEntradaRepositorio.saveAndFlush(it);

            // Atualiza o estoque e os preços do produto
            Optional<Produtos> prod = produtosRepositorio.findById(it.getProdutos().getId());
            if (prod.isPresent()) {
                Produtos produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.setPrecoVenda(it.getValor());
                produto.setPrecoCusto(it.getValorCusto());
                produtosRepositorio.saveAndFlush(produto);
            }
        }
        listaItemEntrada.clear(); // Limpa a lista de itens após salvar
    }
}

