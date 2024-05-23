package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.Produtos;

@Repository
public interface ProdutosRepositorio extends JpaRepository<Produtos, Long> {

}
