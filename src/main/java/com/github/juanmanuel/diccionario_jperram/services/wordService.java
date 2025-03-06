package com.github.juanmanuel.diccionario_jperram.services;

import com.github.juanmanuel.diccionario_jperram.exceptions.NotFoundException;
import com.github.juanmanuel.diccionario_jperram.models.Definition;
import com.github.juanmanuel.diccionario_jperram.models.Word;
import com.github.juanmanuel.diccionario_jperram.repositories.wordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class wordService {
    @Autowired
    private wordRepository wordRepo;

    @Autowired
    private definitionService defService;

    public List<Word> getAll() {return wordRepo.findAll();}

    public Word getById(Long id) throws NotFoundException {
        Optional<Word> word = wordRepo.findById(id);
        if (word.isPresent()) {
            return word.get();
        } else {
            throw new NotFoundException("Word not found with id: " + id, word);
        }
    }

    public Word getWithDefs(Long w) throws NotFoundException {
        Word word = getById(w);
        word.setDefinitions(defService.getByWord(word));
        return word;
    }

    public List<Word> getByCat(String cate) {
        return wordRepo.findByCat(cate);
    }

    public List<Word> getByInitial(String letter) {
        return wordRepo.findByInitial(letter);
    }

    public Word create(Word word) {
        return wordRepo.save(word);
    }

    public Word createWordAndDefs(Word word) {
        Word createdWord = wordRepo.save(word);
        for (Definition definition : word.getDefinitions()) {
            definition.setWord(createdWord);
            defService.create(definition);
        }
        return createdWord;
    }

    public Word update(Long id, Word updatedWord) throws NotFoundException {
        Optional<Word> word = wordRepo.findById(id);
        if (word.isPresent()) {
            Word w = word.get();
            w.setTerm(updatedWord.getTerm());
            w.setGramatic(updatedWord.getGramatic());
            return wordRepo.save(w);
        } else {
            throw new NotFoundException("Word not found with id: " + id, word);
        }
    }

    public boolean delete(long w) throws NotFoundException {
        if (wordRepo.existsById(w)) {
            wordRepo.deleteById(w);
            return true;
        } else {
            throw new NotFoundException("Word not found with id: " + w, Word.class);
        }
    }
}

