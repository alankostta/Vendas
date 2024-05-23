package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoVendas.sistema.modelos.Venda;

@Repository
public interface VendaRepositorio extends JpaRepository<Venda, Long>{

}
