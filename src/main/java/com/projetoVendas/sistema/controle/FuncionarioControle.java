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
import com.projetoVendas.sistema.modelos.Funcionarios;
import com.projetoVendas.sistema.repositorios.CidadesRepositorio;
import com.projetoVendas.sistema.repositorios.FuncionarioRepositorio;

@Controller
public class FuncionarioControle {

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private CidadesRepositorio cidadesRepositorio;
	
	@GetMapping("/cadastroFuncionarios")
	public ModelAndView cadastrarFuncionarios(Funcionarios funcionarios) {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastroFuncionarios");
		mv.addObject("funcionarios", funcionarios);
		mv.addObject("listaCidades", cidadesRepositorio.findAll());
		return mv;
	}
	@PostMapping("/salvarFuncionarios")
	public ModelAndView salvarFuncionarios(Funcionarios funcionario, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarFuncionarios(funcionario);
		}
		funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastrarFuncionarios(new Funcionarios());
	}
	@GetMapping("/listarFuncionarios")
	public ModelAndView listarFuncionarios() {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/listarFuncionarios");
		List<Funcionarios> funcionarios = funcionarioRepositorio.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}
	@GetMapping("/editarFuncionario/{id}")
	public ModelAndView ediarFuncionario(@PathVariable("id") Long id) {
		Optional<Funcionarios> funcionarios = funcionarioRepositorio.findById(id);
		return cadastrarFuncionarios(funcionarios.get());
	}
	
	@GetMapping("/excluirFuncionario/{id}")
	public ModelAndView excluirFuncionario(@PathVariable("id") Long id) {
		funcionarioRepositorio.deleteById(id);
		return listarFuncionarios();
	}
}
