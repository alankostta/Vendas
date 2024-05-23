package com.projetoVendas.sistema.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.ItemEntrada;

@Repository
public interface ItemEntradaRepositorio extends JpaRepository<ItemEntrada, Long> {
	@Query("SELECT e FROM ItemEntrada e WHERE e.entrada.id = ?1")
	List<ItemEntrada> buscarPorEntrada(Long id);

}
