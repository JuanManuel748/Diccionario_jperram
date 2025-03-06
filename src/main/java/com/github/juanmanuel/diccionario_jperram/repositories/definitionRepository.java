package com.github.juanmanuel.diccionario_jperram.repositories;

import com.github.juanmanuel.diccionario_jperram.models.Definition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface definitionRepository extends JpaRepository<Definition, Long> {
    @Query(
            value="SELECT * FROM Definition AS d WHERE d.word = ?1",
            nativeQuery = true
    )
    List<Definition> findByWord(Long word);
}
