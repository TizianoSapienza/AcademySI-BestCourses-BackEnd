package com.academysi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.academysi.dto.CorsoDto;
import com.academysi.dto.CorsoUpdateDto;
import com.academysi.model.Corso;

public class CorsoMapper {

    public static CorsoDto toDto(Corso corso) {
        CorsoDto dto = new CorsoDto();
        dto.setId(corso.getId());
        dto.setNomeCorso(corso.getNomeCorso());
        dto.setDescrizioneBreve(corso.getDescrizioneBreve());
        dto.setDescrizioneCompleta(corso.getDescrizioneCompleta());
        dto.setDurata(corso.getDurata());
        return dto;
    }

    public static List<CorsoDto> toDtoList(List<Corso> corsi) {
        return corsi.stream()
                    .map(CorsoMapper::toDto)
                    .collect(Collectors.toList());
    }

    public static Corso toEntity(CorsoUpdateDto corsoDto) {
        Corso corso = new Corso();
        corso.setId(corsoDto.getId());
        corso.setNomeCorso(corsoDto.getNomeCorso());
        corso.setDescrizioneBreve(corsoDto.getDescrizioneBreve());
        corso.setDescrizioneCompleta(corsoDto.getDescrizioneCompleta());
        corso.setDurata(corsoDto.getDurata());
        return corso;
    }
}

