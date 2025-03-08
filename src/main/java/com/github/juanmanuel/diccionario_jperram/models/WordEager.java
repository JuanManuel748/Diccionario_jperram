package com.github.juanmanuel.diccionario_jperram.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WordEager extends Word {

    @JsonProperty("definitions")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public List<Definition> getDefinitions() {
        return super.getDefinitions();
    }

    @Override
    public String toString() {
        return "WordEager{" +
                "id=" + getId() +
                ", term='" + getTerm() + '\'' +
                ", gramatic='" + getGramatic() + '\'' +
                ", definitions=" + getDefinitions() +
                '}';
    }

}
