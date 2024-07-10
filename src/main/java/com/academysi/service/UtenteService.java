package com.academysi.service;

import java.util.List;

import com.academysi.dto.UtenteDto;
import com.academysi.dto.UtenteLoginRequestDto;
import com.academysi.dto.UtenteRegistrazioneDto;
import com.academysi.model.Utente;


public interface UtenteService {
	
	void registerUser(UtenteRegistrazioneDto utenteDto);
	
	boolean existUserByEmail(String email);
	
	boolean login(UtenteLoginRequestDto utenteLoginRequest);
	
	UtenteDto getUserDtoById(int id);
	
	UtenteDto getUserDtoByEmail(String email);
	
	Utente getUserByEmail(String email);
	
	List<UtenteDto> getUsers();
	
	void updateUserByEmail(String email, UtenteDto utenteDto);
	
	void deleteUserById(int id);
	
	void deleteUserByEmail(String email);
	
	void subscribeToCorso(int utenteId, int corsoId);
	
    void unsubscribeFromCorso(int utenteId, int corsoId);
	
}
