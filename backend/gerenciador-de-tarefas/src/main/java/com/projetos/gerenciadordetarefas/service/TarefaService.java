package com.projetos.gerenciadordetarefas.service;

import com.projetos.gerenciadordetarefas.model.Tarefa;
import com.projetos.gerenciadordetarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Metodo para listar todos as tarefas
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    //Metodo para criar uma nova tarefa
    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // Metodo para atualizar uma tarefa existente
    public Optional<Tarefa> atualizarTarefa(Long id, Tarefa tarefaDetalhes) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setTitulo(tarefaDetalhes.getTitulo());
            tarefa.setDescricao(tarefaDetalhes.getDescricao());
            tarefa.setConcluida(tarefaDetalhes.isConcluida());
            tarefa.setDataConclusao(tarefa.isConcluida() ? LocalDateTime.now() : null);
            return tarefaRepository.save(tarefa);
        });
    }

    // Metodo para excluir uma tarefa pelo ID
    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }


}
