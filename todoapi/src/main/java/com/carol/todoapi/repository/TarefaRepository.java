package com.carol.todoapi.repository;


import com.carol.todoapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    //Métodos customizados (o Spring cria automaticamente)
    List<Tarefa> findByConcluida(Boolean concluida);
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);
}

