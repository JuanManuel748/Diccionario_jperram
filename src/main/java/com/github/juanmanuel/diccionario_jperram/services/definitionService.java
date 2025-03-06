package com.github.juanmanuel.diccionario_jperram.services;

import com.github.juanmanuel.diccionario_jperram.exceptions.NotFoundException;
import com.github.juanmanuel.diccionario_jperram.models.Definition;
import com.github.juanmanuel.diccionario_jperram.models.Word;
import com.github.juanmanuel.diccionario_jperram.repositories.definitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class definitionService {

    @Autowired
    private definitionRepository defRepo;

    public List<Definition> getAll() {
        return defRepo.findAll();
    }

    public Definition getById(Long id) {
        return defRepo.findById(id).orElseThrow(() -> new NotFoundException("Definition not found with id: " + id, Definition.class));
    }

    public List<Definition> getByWord(Word w) {
        return defRepo.findByWord(w.getId());
    }

    public Definition create(Definition definition) {
        return defRepo.save(definition);
    }

    public Definition update(Long id, Definition updatedDefinition) throws NotFoundException {
        if (defRepo.existsById(id)) {
            updatedDefinition.setId(id);
            return defRepo.save(updatedDefinition);
        } else {
            throw new NotFoundException("Definition not found with id: " + id, Definition.class);
        }
    }

    public boolean delete(Long d) throws NotFoundException {
        if (defRepo.existsById(d)) {
            defRepo.deleteById(d);
            return true;
        } else {
            throw new NotFoundException("Definition not found with id: " + d, Definition.class);
        }
    }
}
