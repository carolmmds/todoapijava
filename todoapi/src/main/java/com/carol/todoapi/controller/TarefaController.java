package com.carol.todoapi.controller;

import com.carol.todoapi.model.Tarefa;
import com.carol.todoapi.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})

@RestController
@RequestMapping("/api/tarefas")

public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRepository;

    //Get /api/tarefas -> lista todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodas(){
        return tarefaRepository.findAll();
    }

    // GET /api/tarefas/{id} - Buscar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST /api/tarefas -> criar nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa){
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        return ResponseEntity.ok(tarefaSalva);
    }

    //PUT /api/tarefas/{id} -> atualizar tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefaAtualizada){
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);

        if (tarefaExistente.isPresent()) {
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setConcluida(tarefaAtualizada.getConcluida());

            Tarefa tarefaSalva = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefaSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //DELETE /api/tarefas/{id} -> deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/tarefas/concluidas - Listar apenas tarefas concluídas
    @GetMapping("/concluidas")
    public List<Tarefa> listarTarefasConcluidas() {
        return tarefaRepository.findByConcluida(true);
    }

    // GET /api/tarefas/pendentes - Listar apenas tarefas pendentes
    @GetMapping("/pendentes")
    public List<Tarefa> listarPendentes() {
        return tarefaRepository.findByConcluida(false);
    }

}
