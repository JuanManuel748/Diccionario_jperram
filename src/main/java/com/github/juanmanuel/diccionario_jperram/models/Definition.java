package com.github.juanmanuel.diccionario_jperram.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "definition")
public class Definition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Lob
    @Column(name = "example")
    private String example;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Word word;


    public Definition(Long id, Word word, String example, String description) {
        this.id = id;
        this.word = word;
        this.example = example;
        this.description = description;
    }

    public Definition() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", example='" + example + '\'' +
                ", word=" + word.getTerm() +
                '}';
    }
}