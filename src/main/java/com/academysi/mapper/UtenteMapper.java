package com.academysi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.academysi.dto.UtenteDto;
import com.academysi.model.Ruolo;
import com.academysi.model.Tipologia;
import com.academysi.model.Utente;

public class UtenteMapper {
    public static UtenteDto toDto(Utente utente) {
        UtenteDto dto = new UtenteDto();
        dto.setFirstname(utente.getFirstname());
        dto.setLastname(utente.getLastname());
        dto.setEmail(utente.getEmail());
        
        List<Tipologia> roles = utente.getRuoli().stream()
                                       .map(Ruolo::getTipologia)
                                       .collect(Collectors.toList());
        dto.setRoles(roles);
        
        return dto;
    }

    public static List<UtenteDto> toDtoList(List<Utente> utenti) {
        return utenti.stream()
                     .map(UtenteMapper::toDto)
                     .collect(Collectors.toList());
    }
}
