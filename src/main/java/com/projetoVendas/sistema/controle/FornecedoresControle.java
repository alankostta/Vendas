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

import com.projetoVendas.sistema.modelos.Fornecedores;
import com.projetoVendas.sistema.repositorios.CidadesRepositorio;
import com.projetoVendas.sistema.repositorios.FornecedoresRepositorios;

@Controller
public class FornecedoresControle {

	@Autowired
	private FornecedoresRepositorios fornecedoresRepositorio;
	
	@Autowired
	private CidadesRepositorio cidadesRepositorio;
	
	@GetMapping("/cadastroFornecedores")
	public ModelAndView cadastrarFornecedores(Fornecedores fornecedores) {
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/cadastroFornecedores");
		mv.addObject("fornecedores", fornecedores);
		mv.addObject("listaCidades", cidadesRepositorio.findAll());
		return mv;
	}
	@PostMapping("/salvarFornecedores")
	public ModelAndView salvarClientes(Fornecedores fornecedores, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarFornecedores(fornecedores);
		}
		fornecedoresRepositorio.saveAndFlush(fornecedores);
		return cadastrarFornecedores(new Fornecedores());
	}
	@GetMapping("/listarFornecedores")
	public ModelAndView listarFornecedores() {
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/listarFornecedores");
		List<Fornecedores> fornecedores = fornecedoresRepositorio.findAll();
		mv.addObject("fornecedores", fornecedores);
		return mv;
	}
	@GetMapping("/editarFornecedor/{id}")
	public ModelAndView ediarCliente(@PathVariable("id") Long id) {
		Optional<Fornecedores> fornecedores = fornecedoresRepositorio.findById(id);
		return cadastrarFornecedores(fornecedores.get());
	}
	
	@GetMapping("/excluirFornecedor/{id}")
	public ModelAndView excluirCliente(@PathVariable("id") Long id) {
		fornecedoresRepositorio.deleteById(id);
		return listarFornecedores();
	}
}
