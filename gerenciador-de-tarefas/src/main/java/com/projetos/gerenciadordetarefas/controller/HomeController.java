package com.projetos.gerenciadordetarefas.controller;

import com.projetos.gerenciadordetarefas.model.Task;
import com.projetos.gerenciadordetarefas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TaskService taskService;

    // Página inicial
    @GetMapping("/")
    public String home() {
        return "index";  // Retorna o arquivo index.html de src/main/resources/templates/
    }

    // Página de dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "fragments/dashboard :: content";  // Retorna o fragmento dashboard.html
    }

    // Página de projetos
    @GetMapping("/projects")
    public String projects() {
        return "fragments/projects :: content";  // Retorna o fragmento projects.html
    }

    // Página de tarefas (retorna o fragmento HTML para a interface)
    @GetMapping("/tasks/view")
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "fragments/tasks :: content";  // Retorna o fragmento para exibir as tarefas no frontend
    }

    // Página de equipe
    @GetMapping("/team")
    public String team() {
        return "fragments/team :: content";  // Retorna o fragmento team.html
    }

    // Página de configurações
    @GetMapping("/settings")
    public String settings() {
        return "fragments/settings :: content";  // Retorna o fragmento settings.html
    }
}
