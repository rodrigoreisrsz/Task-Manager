package com.reis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String nome;
    private String descricao;
    private String data;
    private Status status;

    public Tasks() {
        // construtor vazio — o Hibernate exige, pra ele conseguir instanciar o objeto antes de preencher os campos
    }

    public Tasks(String nome, String descricao, String data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.status = Status.NAO_INICIADO;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n Nome: " + nome + "\n Data: " + data + "\n Descrição: "+ descricao + "\n Status: " + status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getId(){
        return id;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }

    public String getData() {
        return data;
    }
}




