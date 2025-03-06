package com.github.juanmanuel.diccionario_jperram.repositories;

import com.github.juanmanuel.diccionario_jperram.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface wordRepository extends JpaRepository<Word, Long> {
    @Query(
            value="SELECT * FROM Word AS w WHERE w.term LIKE ?1%",
            nativeQuery = true
    )
    List<Word> findByInitial(String letter);
    @Query(
            value="SELECT * FROM Word AS w WHERE w.gramatic = ?1",
            nativeQuery = true
    )
    List<Word> findByCat(String cat);

}
