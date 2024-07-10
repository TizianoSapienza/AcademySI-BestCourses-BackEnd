package com.academysi.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.academysi.model.Utente;

@Repository
public interface UtenteDao extends CrudRepository<Utente, Integer> {

	//Query methods
	boolean existsByEmail(String email);
	Optional<Utente> findByEmail(String email);
}