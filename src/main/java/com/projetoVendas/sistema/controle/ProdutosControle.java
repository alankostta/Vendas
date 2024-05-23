package com.projetoVendas.sistema.controle;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projetoVendas.sistema.modelos.Produtos;
import com.projetoVendas.sistema.repositorios.ProdutosRepositorio;

@Controller
public class ProdutosControle {

	@Autowired
	ProdutosRepositorio ProdutosRepositorio;
	
	@GetMapping("/cadastroProdutos")
	public ModelAndView cadastrarProdutos(Produtos produtos) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastroProdutos");
		mv.addObject("produtos", produtos);
		return mv;
	}
	@PostMapping("/salvarProdutos")
	public ModelAndView salvarProdutos(Produtos Produtos, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarProdutos(Produtos);
		}
		ProdutosRepositorio.saveAndFlush(Produtos);
		return cadastrarProdutos(new Produtos());
	}
	@GetMapping("/listarProdutos")
	public ModelAndView listarProdutos() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/listarProdutos");
		List<Produtos> produtos = ProdutosRepositorio.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	@GetMapping("/editarProduto/{id}")
	public ModelAndView ediarProduto(@PathVariable("id") Long id) {
		Optional<Produtos> produtos = ProdutosRepositorio.findById(id);
		return cadastrarProdutos(produtos.get());
	}
	
	@GetMapping("/excluirProduto/{id}")
	public ModelAndView excluirProduto(@PathVariable("id") Long id) {
		ProdutosRepositorio.deleteById(id);
		return listarProdutos();
	}
}
