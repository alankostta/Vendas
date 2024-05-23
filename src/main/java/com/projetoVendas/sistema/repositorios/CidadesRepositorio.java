package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.Cidades;

@Repository
public interface CidadesRepositorio extends JpaRepository<Cidades, Long> {

}
