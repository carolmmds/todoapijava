package com.carol.todoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O título é obrigatório") //Not Blank é usado so para Strings
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100) //É igual a $table->string('titulo', 100)
    private String titulo;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "O campo concluída é obrigatório") //Not Null é usado para tipos primitivos (boolean, int...)
    @Column(nullable = false)
    private Boolean concluida = false; //Boolean = pode ser null, boolean = não pode

    @ManyToOne //Muitas tarefas para uma categoria e Cria chave estrangeira no BD
    @JoinColumn(name="categoria_id") //Nome da coluna da chave estrangeira no BD. Na tabela tarefas vai criar: categoria_id BIGINT
    @JsonIgnoreProperties({"tarefas"}) //Evita loop infinito na serialização JSON Quando retornar JSON da Tarefa, ignora o campo tarefas da Categoria
    private Categoria categoria;

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
