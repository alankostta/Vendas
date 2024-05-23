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

import com.projetoVendas.sistema.modelos.Estados;
import com.projetoVendas.sistema.repositorios.EstadosRepositorio;

@Controller
public class EstadosControle {

	@Autowired
	EstadosRepositorio estadosRepositorio;
	
	@GetMapping("/cadastroEstados")
	public ModelAndView cadastrarEstados(Estados estados) {
		ModelAndView mv = new ModelAndView("administrativo/estados/cadastroEstados");
		mv.addObject("estados", estados);
		return mv;
	}
	@PostMapping("/salvarEstado")
	public ModelAndView salvarEstado(Estados estados, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarEstados(estados);
		}
		estadosRepositorio.saveAndFlush(estados);
		return cadastrarEstados(new Estados());
	}
	@GetMapping("/listarEstados")
	public ModelAndView listarEstados() {
		ModelAndView mv = new ModelAndView("administrativo/estados/listarEstados");
		List<Estados> estados = estadosRepositorio.findAll();
		mv.addObject("estados", estados);
		return mv;
	}
	@GetMapping("/editarEstado/{id}")
	public ModelAndView ediarEstado(@PathVariable("id") Long id) {
		Optional<Estados> estado = estadosRepositorio.findById(id);
		return cadastrarEstados(estado.get());
	}
	
	@GetMapping("/excluirEstado/{id}")
	public ModelAndView excluirEstado(@PathVariable("id") Long id) {
		estadosRepositorio.deleteById(id);
		return listarEstados();
	}
}
