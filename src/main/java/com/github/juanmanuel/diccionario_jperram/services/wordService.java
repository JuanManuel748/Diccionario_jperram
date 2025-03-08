package com.github.juanmanuel.diccionario_jperram.services;

import com.github.juanmanuel.diccionario_jperram.exceptions.NotFoundException;
import com.github.juanmanuel.diccionario_jperram.models.Definition;
import com.github.juanmanuel.diccionario_jperram.models.Word;
import com.github.juanmanuel.diccionario_jperram.models.WordEager;
import com.github.juanmanuel.diccionario_jperram.repositories.wordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class wordService {
    @Autowired
    private wordRepository wordRepo;

    @Autowired
    private definitionService defService;

    public List<Word> getAll() {
        return wordRepo.findAll();
    }

    public Word getById(Long id) throws NotFoundException {
        Optional<Word> word = wordRepo.findById(id);
        if (word.isPresent()) {
            return word.get();
        } else {
            throw new NotFoundException("Word not found with id: " + id, word);
        }
    }

    public WordEager getWithDefs(Long id) throws NotFoundException {
        Word word = getById(id);
        List<Definition> definitions = defService.getByWord(word);
        WordEager wordEager = new WordEager();
        wordEager.setId(word.getId());
        wordEager.setTerm(word.getTerm());
        wordEager.setGramatic(word.getGramatic());
        wordEager.setDefinitions(definitions);
        return wordEager;
    }

    public List<WordEager> getAllWithDefinitions() {
        List<Word> words = getAll();
        List<WordEager> wordsEager = new ArrayList<>();
        for (Word word : words) {
            List<Definition> definitions = defService.getByWord(word);
            WordEager wordEager = new WordEager();
            wordEager.setId(word.getId());
            wordEager.setTerm(word.getTerm());
            wordEager.setGramatic(word.getGramatic());
            wordEager.setDefinitions(definitions);
            wordsEager.add(wordEager);
        }
        return wordsEager;
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


    public Word createWordWithDefinitions(Word word) {
        System.out.println(word);
        Word createdWord = wordRepo.save(word);

        List<Definition> savedDefinitions = new ArrayList<>();
        for (Definition definition : word.getDefinitions()) {
            definition.setWord(createdWord);
            savedDefinitions.add(defService.create(definition));
        }

        createdWord.setDefinitions(savedDefinitions);

        return createdWord;
    }

    private WordEager convertToWordEager(Word word) {
        WordEager wordEager = new WordEager();
        wordEager.setId(word.getId());
        wordEager.setTerm(word.getTerm());
        wordEager.setGramatic(word.getGramatic());
        wordEager.setDefinitions(word.getDefinitions());
        return wordEager;
    }
}