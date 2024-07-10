package com.academysi.service;

import java.util.List;

import com.academysi.dto.CorsoDto;
import com.academysi.dto.CorsoUpdateDto;
import com.academysi.model.Corso;

public interface CorsoService {

	void registerCorso(Corso corso);

    Corso getCorsoById(int id);

    void updateCorso(int id, CorsoUpdateDto corso);

    List<CorsoDto> getCorsi();

    void deleteCorsoById(int id);

	List<CorsoDto> findCorsiByNome(String nome);

	List<CorsoDto> findCorsiByDurata(int durata);
	
	List<CorsoDto> findCorsiByCategoriaId(int categoriaId);
}
