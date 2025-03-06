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

import java.util.List;

@RestController
@RequestMapping("/definition")
public class definitionController {
    @Autowired
    private definitionService defService;
    @Autowired
    private wordService wordService;

    @GetMapping("/{id}")
    public Definition getById(@PathVariable Long id) throws NotFoundException {
        return defService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<Definition>> getAll() {
        List<Definition> definitions = defService.getAll();
        return new ResponseEntity<>(definitions, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/word/{id}/definitions")
    public ResponseEntity<Word> getDefinitionsByWordId(@PathVariable Long id) throws NotFoundException {
        Word word = wordService.getById(id);
        List<Definition> definitions = defService.getByWord(word);
        word.setDefinitions(definitions);
        return new ResponseEntity<>(word, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Definition> create(@RequestBody Definition definition) {
        Definition createdDefinition = defService.create(definition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Definition> update(@PathVariable Long id, @RequestBody Definition updatedDefinition) throws NotFoundException {
        Definition definitionUpdated = defService.update(id, updatedDefinition);
        return ResponseEntity.status(HttpStatus.OK).body(definitionUpdated);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) throws NotFoundException {
        if (defService.delete(id)) {
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
