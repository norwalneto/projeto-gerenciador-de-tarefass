package com.projetos.gerenciadordetarefas.service;

import com.projetos.gerenciadordetarefas.Repository.TaskRepository;
import com.projetos.gerenciadordetarefas.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();  // Busca todas as tarefas do banco de dados
    }

    public Task addTask(Task newTask) {
        return taskRepository.save(newTask);  // Salva a nova tarefa no banco de dados
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);  // Exclui a tarefa pelo ID
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);  // Busca uma tarefa pelo ID
    }

    public void updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            taskRepository.save(task);  // Atualiza a tarefa no banco de dados
        }
    }
}
