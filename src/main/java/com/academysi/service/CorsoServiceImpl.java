package com.academysi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academysi.dao.CategoriaDao;
import com.academysi.dao.CorsoDao;
import com.academysi.dto.CorsoDto;
import com.academysi.dto.CorsoUpdateDto;
import com.academysi.mapper.CorsoMapper;
import com.academysi.model.Categoria;
import com.academysi.model.Corso;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CorsoServiceImpl implements CorsoService {

    @Autowired
    private CorsoDao corsoDao;
    
    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public void registerCorso(Corso corso) {
        corsoDao.save(corso);
    }
    
    @Override
    public List<CorsoDto> getCorsi() {
        List<Corso> corsi = (List<Corso>) corsoDao.findAll();
        return CorsoMapper.toDtoList(corsi);
    }

    @Override
    public Corso getCorsoById(int id) {
        return corsoDao.findById(id).orElse(null);
    }

    @Override
    public void updateCorso(int id, CorsoUpdateDto corsoDto) {
    	
        Optional<Corso> corsoOptional = corsoDao.findById(id);
        if (corsoOptional.isPresent()) {
        	
            Corso corso = corsoOptional.get();
            corso.setNomeCorso(corsoDto.getNomeCorso());
            corso.setDescrizioneBreve(corsoDto.getDescrizioneBreve());
            corso.setDescrizioneCompleta(corsoDto.getDescrizioneCompleta());
            corso.setDurata(corsoDto.getDurata());
            
            corsoDao.save(corso);
        } else {
            throw new EntityNotFoundException("Corso with id " + id + " not found");
        }
    }

    @Override
    public void deleteCorsoById(int id) {
        corsoDao.deleteById(id);
    }
    
    public List<CorsoDto> findCorsiByNome(String nomeCorso) {
        List<Corso> corsi = corsoDao.findByNomeCorsoContainingIgnoreCase(nomeCorso);
        return CorsoMapper.toDtoList(corsi);
    }

    public List<CorsoDto> findCorsiByDurata(int durata) {
        List<Corso> corsi = corsoDao.findByDurata(durata);
        return CorsoMapper.toDtoList(corsi);
    }
    
    @Override
    public List<CorsoDto> findCorsiByCategoriaId(int categoriaId) {
        Optional<Categoria> categoriaOptional = categoriaDao.findById(categoriaId);
        if (!categoriaOptional.isPresent()) {
            throw new EntityNotFoundException("Categoria with ID " + categoriaId + " not found");
        }
        Categoria categoria = categoriaOptional.get();
        List<Corso> corsi = corsoDao.findByCategoria(categoria);
        return CorsoMapper.toDtoList(corsi);
    }
    
}
