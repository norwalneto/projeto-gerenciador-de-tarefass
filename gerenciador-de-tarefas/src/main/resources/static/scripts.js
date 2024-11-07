let isEditing = false;  // Variável global para controlar se estamos editando uma tarefa
let taskIdBeingEdited = null;  // Variável global para armazenar o ID da tarefa sendo editada

// Função para carregar conteúdo dinamicamente na área principal
function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.querySelector('.main-content').innerHTML = data;

            // Após carregar o conteúdo, configurar eventos (como submissão do formulário)
            if (url === '/tasks/view') {
                setupTaskForm();  // Configura o formulário para adicionar tarefas
                loadTasks();      // Carrega as tarefas para exibição
            }
        })
        .catch(error => console.error('Erro ao carregar o conteúdo:', error));
}

// Capturar o clique nos links da barra lateral e carregar o conteúdo correto
document.getElementById('dashboard-link').addEventListener('click', function() {
    loadContent('/dashboard');
});

document.getElementById('projects-link').addEventListener('click', function() {
    loadContent('/projects');
});

document.getElementById('tasks-link').addEventListener('click', function() {
    loadContent('/tasks/view');
});

document.getElementById('team-link').addEventListener('click', function() {
    loadContent('/team');
});

document.getElementById('settings-link').addEventListener('click', function() {
    loadContent('/settings');
});

// Função para carregar e exibir as tarefas existentes como cards e atualizar o gráfico
function loadTasks() {
    fetch('/api/tasks')
        .then(response => response.json())
        .then(data => {
            let taskList = document.getElementById('taskList');
            taskList.innerHTML = '';  // Limpa a lista de tarefas

            // Variáveis para contar o status das tarefas
            let completedCount = 0;
            let inProgressCount = 0;
            let pendingCount = 0;

            // Processa cada tarefa
            data.forEach(task => {
                let taskCard = `
                <div class="task-card">
                    <h3>${task.title}</h3>
                    <p>${task.description}</p>
                    <p>Status: ${task.status}</p>
                    <button class="btn-complete" onclick="completeTask(${task.id})">Concluir</button>
                    <button class="btn-edit" onclick="editTask(${task.id})">Editar</button>
                    <button class="btn-delete" onclick="deleteTask(${task.id})">Excluir</button>
                </div>`;
                taskList.innerHTML += taskCard;

                // Contar as tarefas por status
                if (task.status === 'Concluído') {
                    completedCount++;
                } else if (task.status === 'Em Progresso') {
                    inProgressCount++;
                } else if (task.status === 'Pendente') {
                    pendingCount++;
                }
            });

            // Atualiza o gráfico com os dados dinâmicos
            loadChart(completedCount, inProgressCount, pendingCount);
        })
        .catch(error => console.error('Erro ao carregar tarefas:', error));
}

// Função para configurar o formulário de tarefas
function setupTaskForm() {
    // Adicionar evento de submissão para o formulário
    document.getElementById('taskForm').addEventListener('submit', function (event) {
        event.preventDefault();  // Impede o reload da página

        // Captura os dados do formulário
        let task = captureFormData();

        // Verificar se estamos editando ou criando uma nova tarefa
        if (isEditing) {
            // Atualizando uma tarefa existente
            updateTask(taskIdBeingEdited, task);
        } else {
            // Criando uma nova tarefa
            createTask(task);
        }

        // Resetar estado de edição
        isEditing = false;
        taskIdBeingEdited = null;
    });
}

// Função para capturar os dados do formulário
function captureFormData() {
    let title = document.getElementById('title').value;
    let description = document.getElementById('description').value;
    let status = document.getElementById('status').value;

    return {
        title: title,
        description: description,
        status: status
    };
}

// Função para criar uma nova tarefa
function createTask(task) {
    fetch('/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao adicionar tarefa');
            }
            return response.json();
        })
        .then(data => {
            loadTasks();  // Recarrega a lista de tarefas após a criação
            document.getElementById('taskForm').reset();  // Limpa o formulário
        })
        .catch(error => console.error('Erro ao adicionar tarefa:', error));
}

// Função para atualizar uma tarefa existente
function updateTask(id, task) {
    fetch(`/api/tasks/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao atualizar a tarefa');
            }
            return response.json();
        })
        .then(() => {
            loadTasks();  // Recarrega a lista de tarefas após a atualização
            document.getElementById('taskForm').reset();  // Limpa o formulário
        })
        .catch(error => console.error('Erro ao atualizar tarefa:', error));
}

// Função para excluir tarefa
function deleteTask(id) {
    fetch(`/api/tasks/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao excluir a tarefa');
            }
            loadTasks();  // Recarrega a lista de tarefas após exclusão
        })
        .catch(error => console.error('Erro ao excluir tarefa:', error));
}

// Função para editar Tarefas
function editTask(id) {
    fetch(`/api/tasks/${id}`)
        .then(response => response.json())
        .then(task => {
            // Preencher o formulário com os dados da tarefa
            document.getElementById('title').value = task.title;
            document.getElementById('description').value = task.description;
            document.getElementById('status').value = task.status;

            // Definir o estado de edição
            isEditing = true;
            taskIdBeingEdited = id;
        })
        .catch(error => console.error('Erro ao carregar tarefa para edição:', error));
}

// Função para marcar tarefa como concluída (atualizar o status para "Concluído")
function completeTask(id) {
    let task = {
        status: 'Concluído'
    };

    fetch(`/api/tasks/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao concluir a tarefa');
            }
            loadTasks();  // Recarrega a lista de tarefas após marcar como concluída
        })
        .catch(error => console.error('Erro ao concluir tarefa:', error));
}

// Função para carregar e exibir o gráfico
function loadChart(completed, inProgress, pending) {
    const ctx = document.getElementById('myChart').getContext('2d');

    // Exemplo de gráfico de pizza com dados dinâmicos
    const myChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Concluído', 'Em Progresso', 'Pendente'],
            datasets: [{
                label: 'Status das Tarefas',
                data: [completed, inProgress, pending],  // Usando os dados dinâmicos
                backgroundColor: [
                    '#27ae60', // Concluído
                    '#f1c40f', // Em Progresso
                    '#e74c3c'  // Pendente
                ],
                borderColor: [
                    '#fff', '#fff', '#fff'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true
        }
    });
}
