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

import com.projetoVendas.sistema.modelos.Cidades;
import com.projetoVendas.sistema.repositorios.CidadesRepositorio;
import com.projetoVendas.sistema.repositorios.EstadosRepositorio;

@Controller
public class CidadesControle {
	
	@Autowired
	private CidadesRepositorio cidadesRepositorio;
	
	@Autowired
	private EstadosRepositorio estadosRepositorio;
	
	@GetMapping("/cadastroCidades")
	public ModelAndView cadastrarCidades(Cidades cidades) {
		ModelAndView mv = new ModelAndView("administrativo/cidades/cadastroCidades");
		mv.addObject("cidades", cidades);
		mv.addObject("listaEstados", estadosRepositorio.findAll());
		return mv;
	}
	@PostMapping("/salvarCidade")
	public ModelAndView salvarCidade(Cidades cidades, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarCidades(cidades);
		}
		cidadesRepositorio.saveAndFlush(cidades);
		return cadastrarCidades(new Cidades());
	}
	@GetMapping("/listarCidades")
	public ModelAndView listarCidades() {
		ModelAndView mv = new ModelAndView("administrativo/cidades/listarCidades");
		List<Cidades> cidades = cidadesRepositorio.findAll();
		mv.addObject("cidades", cidades);
		return mv;
	}
	@GetMapping("/editarCidade/{id}")
	public ModelAndView ediarCidade(@PathVariable("id") Long id) {
		Optional<Cidades> cidade = cidadesRepositorio.findById(id);
		return cadastrarCidades(cidade.get());
	}
	
	@GetMapping("/excluirCidade/{id}")
	public ModelAndView excluirCidade(@PathVariable("id") Long id) {
		cidadesRepositorio.deleteById(id);
		return listarCidades();
	}
}
