package com.projetoVendas.sistema.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalControle {
	
	@GetMapping("/pageHome")
	public String homePage() {
		return "/index";
	}
	
	@GetMapping("/administrativo")
	public String acessarPrincipal() {
		return "/administrativo/admHome";
	}
	@GetMapping("/informacao")
	public String infoPage() {
		return "/info";
	}
	@GetMapping("/teste")
	public String testePage() {
		return "/teste";
	}
	@GetMapping("/propriedades")
	public String propriedadesPage() {
		return "/propriedades";
	}
	@GetMapping("/opcoes")
	public String opcoesPage() {
		return "/opcoes";
	}
	@GetMapping("/login")
	public String loginPage() {
		return "/login";
	}
}
