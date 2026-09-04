package com.reis.controllers;

import com.reis.dto.TaskCreateDTO;
import com.reis.dto.TaskStatusDTO;
import com.reis.entities.Tasks;
import com.reis.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service){
        this.service =service;
    }

    @GetMapping
    public List<Tasks> listar(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Tasks buscaPorId(@PathVariable int id){
        return service.verPorId(id);
    }

    @PostMapping
    public Tasks criar(@RequestBody TaskCreateDTO taskCreateDTO){
       return service.adicionarTask(taskCreateDTO.getNome(), taskCreateDTO.getDescricao(), taskCreateDTO.getData());

    }
    @PutMapping("/{id}")
    public Tasks editar(@PathVariable int id, @RequestBody TaskCreateDTO dto){
        return service.editarTask(id, dto.getNome(), dto.getDescricao(), dto.getData());
    }
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id){
        service.deletarTask(id);
    }
    @PatchMapping("/{id}/status")
    public Tasks mudarStatus(@PathVariable int id, @RequestBody TaskStatusDTO dto){
        return service.marcarStatus(id, dto.getStatus());

    }
}
