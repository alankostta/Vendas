package com.projetoVendas.sistema.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projetoVendas.sistema.modelos.Clientes;
import com.projetoVendas.sistema.modelos.Funcionarios;
import com.projetoVendas.sistema.modelos.ItemVenda;
import com.projetoVendas.sistema.modelos.Produtos;
import com.projetoVendas.sistema.modelos.Venda;
import com.projetoVendas.sistema.repositorios.ClientesRepositorio;
import com.projetoVendas.sistema.repositorios.FuncionarioRepositorio;
import com.projetoVendas.sistema.repositorios.ItemVendaRepositorio;
import com.projetoVendas.sistema.repositorios.ProdutosRepositorio;
import com.projetoVendas.sistema.repositorios.VendaRepositorio;

@Controller
public class VendaControle {

	@Autowired
	private VendaRepositorio vendaRepositorio;
	@Autowired
	private ItemVendaRepositorio itemVendaRepositorio;
	@Autowired
	private ProdutosRepositorio produtosRepositorio;
	@Autowired
	private ClientesRepositorio clientesRepositorio;
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	private List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();

	@GetMapping("/cadastroVenda")
	public ModelAndView cadastrarVenda(Venda venda, ItemVenda itemVenda ) {
		ModelAndView mv = new ModelAndView("administrativo/venda/cadastroVenda");
		List<Clientes> clientes  = clientesRepositorio.findAll();
		List<Funcionarios> funcionarios = funcionarioRepositorio.findAll();
		List<Produtos> produtos = produtosRepositorio.findAll();
		mv.addObject("venda", venda);
		mv.addObject("itemVenda", itemVenda);
		mv.addObject("listaItemVenda", this.listaItemVenda);
		mv.addObject("listaFuncionarios", funcionarios);
		mv.addObject("listaClientes", clientes);
		mv.addObject("listaProdutos", produtos);
		return mv;
	}

	@PostMapping("/salvarVenda")
	public ModelAndView salvarVenda(String acao, Venda venda, ItemVenda itemVenda, BindingResult result) {
		
		venda.setCliente(venda.getCliente());
		
		if (result.hasErrors()) {
			return cadastrarVenda(venda, itemVenda);
		}
		if(acao.equals("itens")) {
			
			itemVenda.setValor(itemVenda.getProdutos().getPrecoVenda());
			itemVenda.setSubTotal(itemVenda.getProdutos().getPrecoVenda() * itemVenda.getQuantidade());
			venda.setValorTotal(venda.getValorTotal() + (itemVenda.getValor() * itemVenda.getQuantidade()));
			venda.setQuantidadeTotal(venda.getQuantidadeTotal() + itemVenda.getQuantidade());
			this.listaItemVenda.add(itemVenda);
			
		}else if(acao.equals("salvar")) {
			vendaRepositorio.saveAndFlush(venda);
			
			for(ItemVenda it: listaItemVenda) {
				it.setVenda(venda);
				itemVendaRepositorio.saveAndFlush(it);
				
				Optional<Produtos> prod = produtosRepositorio.findById(it.getProdutos().getId());
				Produtos produto = prod.get();
				produto.setEstoque(produto.getEstoque() - it.getQuantidade());
				produto.setPrecoVenda(it.getValor());
				produtosRepositorio.saveAndFlush(produto);
				
				this.listaItemVenda = new ArrayList<ItemVenda>();
			}
			return cadastrarVenda(new Venda(), new ItemVenda());
		}
		return cadastrarVenda(venda, new ItemVenda());
	}

	@GetMapping("/listarVenda")
	public ModelAndView listarVenda() {
		ModelAndView mv = new ModelAndView("administrativo/venda/listarVenda");
		List<Venda> vendas = vendaRepositorio.findAll();
		mv.addObject("listaVendas", vendas);
		return mv;
	}

	@GetMapping("/editarVenda/{id}")
	public ModelAndView ediarVenda(@PathVariable("id") Long id) {
		Optional<Venda> vendas = vendaRepositorio.findById(id);
		this.listaItemVenda = itemVendaRepositorio.buscarPorVenda(id);
		return cadastrarVenda(vendas.get(), new ItemVenda());
	}

	@GetMapping("/excluirVenda/{id}")
	public ModelAndView excluirVenda(@PathVariable("id") Long id) {
		vendaRepositorio.deleteById(id);
		return listarVenda();
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}
}
