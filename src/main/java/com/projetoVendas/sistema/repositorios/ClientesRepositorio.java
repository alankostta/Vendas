package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.Clientes;

@Repository
public interface ClientesRepositorio extends JpaRepository<Clientes, Long> {

}
