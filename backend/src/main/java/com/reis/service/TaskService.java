package com.reis.service;

import com.reis.entities.Status;
import com.reis.entities.Tasks;
import com.reis.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Tasks adicionarTask(String nome, String descricao, String data) {
        Tasks task  = new Tasks(nome, descricao, data);
        return repository.save(task);
    }

    public void deletarTask(int id) {
        repository.deleteById(id);

    }

    public Tasks editarTask(int id, String nome, String descricao, String data) {
        Tasks taskExistente = repository.findById(id).get();
        taskExistente.setNome(nome);
        taskExistente.setDescricao(descricao);
        taskExistente.setData(data);
        return repository.save(taskExistente);
    }

    public Tasks marcarStatus(int id, Status status) {
        Tasks taskExistente = repository.findById(id).get();
        taskExistente.setStatus(status);
        return repository.save(taskExistente);

    }

    public List<Tasks> listarTodos() {
        return repository.findAll();
    }

    public Tasks verPorId(int id) {
        Tasks task = repository.findById(id).get();
        return task;
    }
}
