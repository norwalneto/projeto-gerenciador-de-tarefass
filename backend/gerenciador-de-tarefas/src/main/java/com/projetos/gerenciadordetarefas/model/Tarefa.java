package com.projetos.gerenciadordetarefas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera automaticamente o ID da tarefa
    private Long id;

    @Column(nullable = false) // O titulo é obrigatório
    private String titulo;

    private String descricao;

    @Column(nullable = false) // Status obrigatório: se a tarefa está concluída ou não
    private boolean concluida;

    @Column(nullable = false) // Data de criação obrigatoria
    private LocalDateTime dataCriacao;

    @Column // Data de conclusão, que pode ser nula se a tarefa não for concluída
    private LocalDateTime dataConclusao;

    // Construtor padrão
    public Tarefa(){
        this.dataCriacao = LocalDateTime.now();
        this.concluida = false;
    }

    // Getter e Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
