package com.academysi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.academysi.model.Categoria;
import com.academysi.model.Corso;

public interface CorsoDao extends CrudRepository<Corso, Integer> {
	
	List<Corso> findByNomeCorsoContainingIgnoreCase(String nomeCorso);
	
	List<Corso> findByDurata(int durata);
	
	List<Corso> findByCategoria(Categoria categoria);
}
