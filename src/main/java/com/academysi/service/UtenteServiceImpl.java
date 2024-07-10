package com.academysi.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academysi.dao.CorsoDao;
import com.academysi.dao.UtenteDao;
import com.academysi.dto.UtenteDto;
import com.academysi.dto.UtenteLoginRequestDto;
import com.academysi.dto.UtenteRegistrazioneDto;
import com.academysi.mapper.UtenteMapper;
import com.academysi.model.Corso;
import com.academysi.model.Utente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService{
	
	@Autowired
	private UtenteDao utenteDao;
	
	@Autowired
	private CorsoDao corsoDao;
	
	@Override
	public void registerUser(UtenteRegistrazioneDto utenteDto) {
	    // Crea un nuovo oggetto Utente
	    Utente utente = new Utente();
	    utente.setFirstname(utenteDto.getFirstname());
	    utente.setLastname(utenteDto.getLastname());
	    utente.setEmail(utenteDto.getEmail());

	    // Hash della password
	    String sha256hex = DigestUtils.sha256Hex(utenteDto.getPassword());
	    utente.setPassword(sha256hex);

	    // Salva l'Utente nel repository
	    utenteDao.save(utente);
	}
	 
	@Override
	public boolean login(UtenteLoginRequestDto utenteLoginRequest) {
		String email = utenteLoginRequest.getEmail();
	    String password = utenteLoginRequest.getPassword();

	    Optional<Utente> utenteOptional = utenteDao.findByEmail(email);

	    if (utenteOptional.isPresent()) {
	    	Utente utente = utenteOptional.get();
	        // Esegui il controllo della password (ad esempio, usando SHA-256)
	        String hashedPassword = DigestUtils.sha256Hex(password);
	            
	        return hashedPassword.equals(utente.getPassword());
	    }

	    return false;
	}

	@Override
	public UtenteDto getUserDtoById(int id) {
	    Utente utente = utenteDao.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
	    return UtenteMapper.toDto(utente);
	}

	@Override
	public List<UtenteDto> getUsers() {
	    List<Utente> utenti = (List<Utente>) utenteDao.findAll();
	    return UtenteMapper.toDtoList(utenti);
	}

	@Override
	public boolean existUserByEmail(String email) {
		return utenteDao.existsByEmail(email);
	}

	@Override
	public Utente getUserByEmail(String email) {
		Optional<Utente> userOptionalDb = utenteDao.findByEmail(email);

        if (!userOptionalDb.isPresent()) {
            return null;
        }

        return userOptionalDb.get();
	}
	
	@Override
    public UtenteDto getUserDtoByEmail(String email) {
        Optional<Utente> userOptionalDb = utenteDao.findByEmail(email);
        if (!userOptionalDb.isPresent()) {
            return null;
        }
        return UtenteMapper.toDto(userOptionalDb.get());
    }

	@Override
	public void updateUserByEmail(String email, UtenteDto utenteDto) {
	    Optional<Utente> userOptionalDb = utenteDao.findByEmail(email);

	    if (userOptionalDb.isPresent()) {
	        Utente userDb = userOptionalDb.get();

	        // Update fields from DTO
	        userDb.setFirstname(utenteDto.getFirstname());
	        userDb.setLastname(utenteDto.getLastname());
	        userDb.setEmail(utenteDto.getEmail());
	        if (utenteDto.getPassword() != null && !utenteDto.getPassword().isEmpty()) {
	            String sha256hex = DigestUtils.sha256Hex(utenteDto.getPassword());
	            userDb.setPassword(sha256hex);
	        }

	        // Save updated entity
	        utenteDao.save(userDb);
	    } else {
	        throw new EntityNotFoundException("User with email " + email + " not found");
	    }
	}
	
	@Override
	public void deleteUserById(int id) {
		Optional<Utente> userOptional = utenteDao.findById(id);

        if (userOptional.isPresent()) {
            utenteDao.deleteById(id);
        }
		
	}

	@Override
    public void deleteUserByEmail(String email) {
        Optional<Utente> userOptionalDb = utenteDao.findByEmail(email);

        if (userOptionalDb.isPresent()) {
            Utente userDb = userOptionalDb.get();
            utenteDao.deleteById(userDb.getId());
        } else {
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
    }
	
	@Override
	public void subscribeToCorso(int utenteId, int corsoId) {
		
	    try {
	        Optional<Utente> utenteOptional = utenteDao.findById(utenteId);
	        if (!utenteOptional.isPresent()) {
	            throw new EntityNotFoundException("Utente with ID " + utenteId + " not found");
	        }

	        Optional<Corso> corsoOptional = corsoDao.findById(corsoId);
	        if (!corsoOptional.isPresent()) {
	            throw new EntityNotFoundException("Corso with ID " + corsoId + " not found");
	        }

	        Utente utente = utenteOptional.get();
	        Corso corso = corsoOptional.get();
	        utente.getCorsi().add(corso);
	        utenteDao.save(utente);
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to subscribe Utente to Corso: " + e.getMessage());
	    }
	}

	@Override
	public void unsubscribeFromCorso(int utenteId, int corsoId) {
		
	    try {
	        Optional<Utente> utenteOptional = utenteDao.findById(utenteId);
	        if (!utenteOptional.isPresent()) {
	            throw new EntityNotFoundException("Utente with ID " + utenteId + " not found");
	        }

	        Optional<Corso> corsoOptional = corsoDao.findById(corsoId);
	        if (!corsoOptional.isPresent()) {
	            throw new EntityNotFoundException("Corso with ID " + corsoId + " not found");
	        }

	        Utente utente = utenteOptional.get();
	        Corso corso = corsoOptional.get();
	        
	        utente.getCorsi().remove(corso);
	        utenteDao.save(utente);
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to unsubscribe Utente from Corso: " + e.getMessage());
	    }
	}

}