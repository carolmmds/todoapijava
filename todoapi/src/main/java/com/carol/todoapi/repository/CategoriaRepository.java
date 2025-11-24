package com.carol.todoapi.repository;

import com.carol.todoapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Buscar categoria por nome
    Optional<Categoria> findByNome(String nome);

    // Verificar se existe categoria com esse nome
    boolean existsByNome(String nome);
}
