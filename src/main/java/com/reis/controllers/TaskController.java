package com.reis.controllers;

import com.reis.dto.TaskCreateDTO;
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


    @PostMapping

    public Tasks criar(@RequestBody TaskCreateDTO taskCreateDTO){
       return service.adicionarTask(taskCreateDTO.getNome(), taskCreateDTO.getDescricao(), taskCreateDTO.getData());

    }
//    @PutMapping("/{id}")
////    public Tasks editar(@PathVariable Long id, @RequestBody TaskCreateDTO dto){
//////        TaskCreateDTO taskAtualizada = service.editarTask(id, dto);
//////        return service.editarTask(taskAtualizada);
////    }
//    @DeleteMapping("/{id}")
////    public Tasks deletar(@RequestBody TaskCreateDTO taskCreateDTO){
////       // return service.deletarTask(taskCreateDTO);
////    }
}
