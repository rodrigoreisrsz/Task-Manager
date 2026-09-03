package com.reis.controllers;

import com.reis.entities.Tasks;
import com.reis.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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



}
