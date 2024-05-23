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
import com.projetoVendas.sistema.modelos.Clientes;
import com.projetoVendas.sistema.repositorios.CidadesRepositorio;
import com.projetoVendas.sistema.repositorios.ClientesRepositorio;

@Controller
public class ClientesControle {

	@Autowired
	private ClientesRepositorio clientesRepositorio;
	
	@Autowired
	private CidadesRepositorio cidadesRepositorio;
	
	@GetMapping("/cadastroClientes")
	public ModelAndView cadastrarClientes(Clientes clientes) {
		ModelAndView mv = new ModelAndView("administrativo/clientes/cadastroClientes");
		mv.addObject("clientes", clientes);
		mv.addObject("listaCidades", cidadesRepositorio.findAll());
		return mv;
	}
	@PostMapping("/salvarClientes")
	public ModelAndView salvarClientes(Clientes clientes, BindingResult result) {
	
		if(result.hasErrors()) {
			return cadastrarClientes(clientes);
		}
		clientesRepositorio.saveAndFlush(clientes);
		return cadastrarClientes(new Clientes());
	}
	@GetMapping("/listarClientes")
	public ModelAndView listarClientes() {
		ModelAndView mv = new ModelAndView("administrativo/clientes/listarClientes");
		List<Clientes> clientes = clientesRepositorio.findAll();
		mv.addObject("clientes", clientes);
		return mv;
	}
	@GetMapping("/editarClientes/{id}")
	public ModelAndView ediarCliente(@PathVariable("id") Long id) {
		Optional<Clientes> clientes = clientesRepositorio.findById(id);
		return cadastrarClientes(clientes.get());
	}
	
	@GetMapping("/excluirCliente/{id}")
	public ModelAndView excluirCliente(@PathVariable("id") Long id) {
		clientesRepositorio.deleteById(id);
		return listarClientes();
	}
}
