package com.carol.todoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String nome;

    @Size(max = 200, message = "A descrição pode ter no máximo 200 caracteres")
    @Column(length = 200)
    private String descricao;

    @Column(length = 7)
    private String cor = "#667eea"; // Cor em hexadecimal para UI

    // Relacionamento OneToMany - Uma categoria tem várias tarefas
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL) //mapedBy = "categoria" -> o campo categoria da classe tarefa controla o relacionamento e CascadeType.ALL -> se apagar a categoria, apaga as tarefas relacionadas
    @JsonIgnore // Evita loop infinito na serialização JSON
    private List<Tarefa> tarefas = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
