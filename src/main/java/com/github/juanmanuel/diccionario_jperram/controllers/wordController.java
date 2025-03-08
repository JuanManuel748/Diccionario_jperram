package com.github.juanmanuel.diccionario_jperram.controllers;

import com.github.juanmanuel.diccionario_jperram.exceptions.NotFoundException;
import com.github.juanmanuel.diccionario_jperram.models.Definition;
import com.github.juanmanuel.diccionario_jperram.models.Word;
import com.github.juanmanuel.diccionario_jperram.models.WordEager;
import com.github.juanmanuel.diccionario_jperram.services.wordService;
import com.github.juanmanuel.diccionario_jperram.services.definitionService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Busca una palabra por su ID")
    @GetMapping("/{id}")
    public Word getById(@PathVariable Long id) throws NotFoundException {
        return wordService.getById(id);
    }

    @GetMapping("/{id}/withdefs")
    public ResponseEntity<WordEager> getWithDefs(@PathVariable long id) throws NotFoundException {
        WordEager word = wordService.getWithDefs(id);
        return ResponseEntity.status(HttpStatus.OK).body(word);
    }

    @Operation(summary = "Muestra todas las palabras")
    @GetMapping
    public ResponseEntity<List<Word>> getAll() {
        List<Word> words = wordService.getAll();
        for (Word word : words) {
            word.setDefinitions(new ArrayList<>());
        }
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Muestra todas las palabras con definiciones")
    @GetMapping("/definitions")
    public ResponseEntity<List<WordEager>> getAllWithDefinitions() {
        List<WordEager> words = wordService.getAllWithDefinitions();
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Crea una nueva palabra")
    @PostMapping
    public ResponseEntity<Word> create(@RequestBody Word word) {
        Word createdWord = wordService.create(word);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWord);
    }

    @Operation(summary = "Actualiza una palabra")
    @PutMapping("/{id}")
    public ResponseEntity<Word> update(@PathVariable Long id, @RequestBody Word updatedWord) throws NotFoundException {
        Word wordUpdated = wordService.update(id, updatedWord);
        return ResponseEntity.status(HttpStatus.OK).body(wordUpdated);
    }

    @Operation(summary = "Busca palabras por su letra inicial")
    @GetMapping("/initial/{letter}")
    public ResponseEntity<List<Word>> getByInitial(@PathVariable String letter) {
        List<Word> words = wordService.getByInitial(letter);
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Busca palabras por su categoría")
    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Word>> getByCat(@PathVariable String cat) {
        List<Word> words = wordService.getByCat(cat);
        return new ResponseEntity<>(words, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Elimina una palabra por su ID")
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) throws NotFoundException {
        if(wordService.delete(id)) {
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @Operation(summary = "Crea una nueva palabra con definiciones")
    @PostMapping("/withdefs")
    public ResponseEntity<Word> createWithDefinitions(@RequestBody Word word) {
        Word wordEager = wordService.createWordWithDefinitions(word);
        return ResponseEntity.status(HttpStatus.CREATED).body(wordEager);
    }

    @Operation(summary = "Crea una nueva definición para una palabra")
    @PostMapping("/{id}/definitions")
    public ResponseEntity<Definition> createNewDefinition(@PathVariable Long id, @RequestBody Definition definition) throws NotFoundException {
        Word word = wordService.getById(id);
        definition.setWord(word);
        Definition createdDefinition = defService.create(definition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinition);
    }
}