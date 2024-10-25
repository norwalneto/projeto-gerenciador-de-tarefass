package com.projetos.gerenciadordetarefas.controller;

import com.projetos.gerenciadordetarefas.model.Tarefa;
import com.projetos.gerenciadordetarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    //Endpoint para listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodas(){
        return tarefaService.listarTodas();
    }

    // Endpoint para criar uma nova tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes){
        return tarefaService.atualizarTarefa(id, tarefaDetalhes).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para excluir uma tarefa existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
