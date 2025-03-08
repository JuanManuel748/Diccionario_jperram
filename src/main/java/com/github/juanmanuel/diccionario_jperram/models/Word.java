package com.github.juanmanuel.diccionario_jperram.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "term", length = 250)
    private String term;

    @Lob
    @NotNull
    @Column(name = "gramatic")
    private String gramatic;

    @OneToMany(mappedBy = "word")
    @JsonManagedReference
    private List<Definition> definitions = new ArrayList<>();


    public Word() {}

    public Word(Long id, String term, String gramatic, List<Definition> definitions) {
        this.id = id;
        this.term = term;
        this.gramatic = gramatic;
        this.definitions = definitions;
    }

    public Word(String term, String gramatic) {
        this.term = term;
        this.gramatic = gramatic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGramatic() {
        return gramatic;
    }

    public void setGramatic(String gramatic) {
        this.gramatic = gramatic;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", gramatic='" + gramatic + '\'' +
                ", definitions=" + definitions +
                '}';
    }


}
