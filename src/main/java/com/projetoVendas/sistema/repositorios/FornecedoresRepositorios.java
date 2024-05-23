package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.Fornecedores;

@Repository
public interface FornecedoresRepositorios extends JpaRepository<Fornecedores, Long> {

}
