package com.github.juanmanuel.diccionario_jperram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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