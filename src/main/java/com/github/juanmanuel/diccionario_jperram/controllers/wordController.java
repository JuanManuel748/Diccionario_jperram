package com.github.juanmanuel.diccionario_jperram.controllers;

import com.github.juanmanuel.diccionario_jperram.exceptions.NotFoundException;
import com.github.juanmanuel.diccionario_jperram.models.Definition;
import com.github.juanmanuel.diccionario_jperram.models.Word;
import com.github.juanmanuel.diccionario_jperram.services.wordService;
import com.github.juanmanuel.diccionario_jperram.services.definitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/word")
public class wordController {
    @Autowired
    private wordService wordService;
    @Autowired
    private definitionService defService;

    @GetMapping("/{id}")
    public Word getById(@PathVariable Long id) throws NotFoundException {
        return wordService.getById(id);
    }

    @GetMapping("/{id}/withdefs")
    public ResponseEntity<Word> getWithDefs(@PathVariable long id) throws NotFoundException {
        Word word = wordService.getWithDefs(id);
        return ResponseEntity.status(HttpStatus.OK).body(word);
    }

    @GetMapping
    public ResponseEntity<List<Word>> getAll() {
        List<Word> words = wordService.getAll();
        for (Word word : words) {
            word.setDefinitions(new ArrayList<>());
        }
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/definitions")
    public ResponseEntity<List<Word>> getAllWithDefinitions() {
        List<Word> words = wordService.getAll();
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Word> create(@RequestBody Word word) {
        Word createdWord = wordService.create(word);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWord);
    }

    @PostMapping("/withdefs")
    public ResponseEntity<Word> createWithDefinitions(@RequestBody Word word) {
        Word createdWord = wordService.createWordAndDefs(word);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Word> update(@PathVariable Long id, @RequestBody Word updatedWord) throws NotFoundException {
        Word wordUpdated = wordService.update(id, updatedWord);
        return ResponseEntity.status(HttpStatus.OK).body(wordUpdated);
    }

    @GetMapping("/initial/{letter}")
    public ResponseEntity<List<Word>> getByInitial(@PathVariable String letter) {
        List<Word> words = wordService.getByInitial(letter);
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Word>> getByCat(@PathVariable String cat) {
        List<Word> words = wordService.getByCat(cat);
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) throws NotFoundException {
        if(wordService.delete(id)) {
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }


    @PostMapping("/{id}/definitions")
    public ResponseEntity<Definition> createNewDefinition(@PathVariable Long id, @RequestBody Definition definition) throws NotFoundException {
        Word word = wordService.getById(id);
        definition.setWord(word);
        Definition createdDefinition = defService.create(definition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinition);
    }

}
