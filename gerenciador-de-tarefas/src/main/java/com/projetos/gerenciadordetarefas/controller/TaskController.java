package com.projetos.gerenciadordetarefas.controller;

import com.projetos.gerenciadordetarefas.model.Task;
import com.projetos.gerenciadordetarefas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // API para obter a lista de tarefas (retorna a lista de tarefas em JSON)
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // API para adicionar uma nova tarefa
    @PostMapping
    public Task addTask(@RequestBody Task newTask) {
        return taskService.addTask(newTask);
    }

    // API para excluir uma tarefa
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    // API para atualizar uma tarefa existente
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        taskService.updateTask(id, updatedTask);
        return updatedTask;
    }
}
