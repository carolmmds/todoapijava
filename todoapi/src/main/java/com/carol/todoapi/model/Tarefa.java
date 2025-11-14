package com.carol.todoapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity //Diz pro JPA: "isso é uma entidade que vai virar tabela no banco" (=extends Model)
@Table (name = "tarefas") //Nome da tabela no banco (=protected $table = 'tarefas')
@Data //Gera automaticamente getters e setters (getID, setID, getTitulo, setTitulo...)
@NoArgsConstructor //Gera construtor vazio: new Tarefa()
@AllArgsConstructor //Gera construtor com todos os campos: new Tarefa(id, titulo, ...)


public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity -> usa auto increment do banco
    private Long id; //Long é tipo numerico q aceita null (diferente de long primitivo)

    @Column(nullable = false, length = 100) //É igual a $table->string('titulo', 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private Boolean concluida = false; //Boolean = pode ser null, boolean = não pode

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist //executa antes de salvar no banco (INSERT)
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate //executa antes de atualizar (UPDATE)
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
