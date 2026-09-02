package com.reis.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.entities.Tasks;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TaskRepository {
    private static final String ARQUIVO = "tasks.json";
    private Gson gson = new Gson();

    public void salvar(ArrayList<Tasks>tasks) throws IOException {
        String json = gson.toJson(tasks);
        try(FileWriter writer = new FileWriter(ARQUIVO)){
            writer.write(json);
        }catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
    public ArrayList<Tasks>carregarTasks() throws IOException {
        FileReader reader = new FileReader(ARQUIVO);
        ArrayList<Tasks> tasks = gson.fromJson(reader, new TypeToken<ArrayList<Tasks>>(){}.getType());
        return tasks;


    }


}
