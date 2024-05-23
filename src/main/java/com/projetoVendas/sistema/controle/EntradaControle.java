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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projetoVendas.sistema.modelos.Entrada;
import com.projetoVendas.sistema.modelos.Fornecedores;
import com.projetoVendas.sistema.modelos.Funcionarios;
import com.projetoVendas.sistema.modelos.ItemEntrada;
import com.projetoVendas.sistema.modelos.Produtos;
import com.projetoVendas.sistema.repositorios.EntradaRepositorio;
import com.projetoVendas.sistema.repositorios.FornecedoresRepositorios;
import com.projetoVendas.sistema.repositorios.FuncionarioRepositorio;
import com.projetoVendas.sistema.repositorios.ItemEntradaRepositorio;
import com.projetoVendas.sistema.repositorios.ProdutosRepositorio;
import com.projetoVendas.sistema.service.EntradaService;

@Controller
public class EntradaControle {

	@Autowired
	ItemEntradaRepositorio itemEntradaRepositorio;
	@Autowired
	EntradaRepositorio entradaRepositorio;
    @Autowired
    private EntradaService entradaService;
    @Autowired
    private ProdutosRepositorio produtosRepositorio;
    @Autowired
    private FornecedoresRepositorios fornecedoresRepositorios;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    private List<ItemEntrada> listaItemEntrada = new ArrayList<>();
    private Entrada entrada = new Entrada();

    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrarEntrada(Entrada entrada, ItemEntrada itemEntrada) {
        return prepareEntradaModelAndView(entrada, itemEntrada, false); // Chama o método para preparar a view
    }

    @GetMapping("/cadastroEntradaClean")
    public ModelAndView cadastrarEntradaClean(Entrada entrada, ItemEntrada itemEntrada) {
        return prepareEntradaModelAndView(entrada, itemEntrada, true); // Chama o método para preparar a view e limpar a lista de itens
    }

    // Método auxiliar para preparar o ModelAndView com os dados necessários
    private ModelAndView prepareEntradaModelAndView(Entrada entrada, ItemEntrada itemEntrada, boolean clearList) {
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastroEntrada");
        List<Fornecedores> fornecedores = fornecedoresRepositorios.findAll();
        List<Funcionarios> funcionarios = funcionarioRepositorio.findAll();
        List<Produtos> produtos = produtosRepositorio.findAll();

        mv.addObject("entrada", entrada);
        mv.addObject("itemEntrada", itemEntrada);
        if (clearList) {
            this.listaItemEntrada.clear();
        }
        mv.addObject("listaItemEntrada", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarios);
        mv.addObject("listaFornecedores", fornecedores);
        mv.addObject("listaProdutos", produtos);

        return mv;
    }

    @GetMapping("/addListaIten/{id}")
    @ResponseBody
    public ModelAndView addListaIten(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        entradaService.adicionarItemNaLista(id, entrada, listaItemEntrada); // Adiciona item à lista usando o serviço
        redirectAttributes.addFlashAttribute("entrada", this.entrada);
        return new ModelAndView("redirect:/cadastroEntrada");
    }

    @GetMapping("/removerItem/{id}")
    public ModelAndView removerItem(@PathVariable Long id) {
        // Remove o item da lista que corresponde ao ID do produto
        listaItemEntrada.removeIf(item -> item.getProdutos().getId().equals(id));
        return new ModelAndView("redirect:/cadastroEntrada");
    }

    @PostMapping("/salvarEntrada")
    public ModelAndView salvarEntrada(String acao, Entrada entrada, ItemEntrada itemEntrada, BindingResult result) {
        if (result.hasErrors()) {
            return prepareEntradaModelAndView(entrada, itemEntrada, false); // Retorna à view com erros de validação
        }
        if ("itens".equals(acao)) {
            // Adiciona item à lista e atualiza os totais
            listaItemEntrada.add(itemEntrada);
            entradaService.atualizarTotaisEntrada(entrada, itemEntrada);
        } else if ("salvar".equals(acao)) {
            // Salva a entrada e seus itens no banco de dados
            entradaService.salvarEntrada(entrada, listaItemEntrada);
            return prepareEntradaModelAndView(new Entrada(), new ItemEntrada(), true); // Limpa o formulário após salvar
        }
        return prepareEntradaModelAndView(entrada, new ItemEntrada(), false); // Retorna à view com a entrada atualizada
    }

    @GetMapping("/listarEntradas")
    public ModelAndView listarEntrada() {
        ModelAndView mv = new ModelAndView("administrativo/entrada/listarEntradas");
        List<Entrada> entradas = entradaRepositorio.findAll(); // Busca todas as entradas no banco de dados
        mv.addObject("listaEntrada", entradas);
        return mv;
    }

    @GetMapping("/editarEntrada/{id}")
    public ModelAndView editarEntrada(@PathVariable("id") Long id) {
        Optional<Entrada> entradaOpt = entradaRepositorio.findById(id);
        if (entradaOpt.isPresent()) {
            this.entrada = entradaOpt.get();
            this.listaItemEntrada = itemEntradaRepositorio.buscarPorEntrada(id); // Busca os itens da entrada
            return prepareEntradaModelAndView(this.entrada, new ItemEntrada(), false);
        }
        return new ModelAndView("redirect:/listarEntradas"); // Redireciona se a entrada não for encontrada
    }

    @GetMapping("/excluirEntrada/{id}")
    public ModelAndView excluirEntrada(@PathVariable("id") Long id) {
        entradaRepositorio.deleteById(id); // Exclui a entrada do banco de dados
        return listarEntrada();
    }
}
