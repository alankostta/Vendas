package com.projetoVendas.sistema.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.projetoVendas.sistema.modelos.ItemVenda;

@Repository
public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long>{

	@Query("SELECT i FROM ItemVenda i WHERE i.venda.id = ?1")
	List<ItemVenda> buscarPorVenda(Long id);
}
