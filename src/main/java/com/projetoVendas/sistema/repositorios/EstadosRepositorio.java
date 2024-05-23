package com.projetoVendas.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.Estados;

@Repository
public interface EstadosRepositorio extends JpaRepository<Estados, Long> {
}
