package com.projetoVendas.sistema.modelos;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Entrada")
public class Entrada implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String obs;
	private Double valorTotal = 0.00;
	private Double quantidadeTotal = 0.00;
	private Date dataEntrada = new Date();
	
	@ManyToOne
	private Fornecedores fornecedor;
	@ManyToOne
	private Funcionarios funcionarios;
	public Entrada() {
		super();
	}
	public Entrada(Long id, String obs, Double valorTotal, Double quantidadeTotal,
			Date dataEntrada, Fornecedores fornecedor, Funcionarios funcionarios) {
		super();
		this.id = id;
		this.obs = obs;
		this.valorTotal = valorTotal;
		this.quantidadeTotal = quantidadeTotal;
		this.dataEntrada = dataEntrada;
		this.fornecedor = fornecedor;
		this.funcionarios = funcionarios;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Double getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(Double quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public Fornecedores getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Funcionarios getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(Funcionarios funcionarios) {
		this.funcionarios = funcionarios;
	}
}
