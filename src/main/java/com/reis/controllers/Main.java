package com.reis.controllers;

import com.reis.service.Menu;
import com.reis.entities.Status;
import com.reis.service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        TaskService manager = new TaskService();
        Menu menu = new Menu();

        int opcao;
        while (true) {
            menu.mostrar();
            opcao = input.nextInt();
            switch (opcao) {
                case 1:
                    input.nextLine();
                    System.out.println("Nome da tarefa:");
                    String nome = input.nextLine();
                    System.out.println("Descrição:");
                    String descricao = input.nextLine();
                    System.out.println("Data:");
                    String data = input.nextLine();
                    manager.adicionarTask(nome, descricao, data);
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Id da tarefa: ");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.println("Faça as alterações.");
                    System.out.println("Nome da tarefa: ");
                    String nomeAlterado = input.nextLine();
                    System.out.println("Descrição:");
                    String descricaoAlterada = input.nextLine();
                    System.out.println("Data:");
                    String dataAlterada = input.nextLine();
                    manager.editarTask(id, nomeAlterado, descricaoAlterada, dataAlterada);
                    break;
                case 3:
                    System.out.println("Id da tarefa: ");
                    int idDeletar = input.nextInt();
                    manager.deletarTask(idDeletar);
                    System.out.println("Tarefa removida com sucesso!");
                    break;
                case 4:
                    System.out.println("Id da tarefa: ");
                    int idMarcarStatus = input.nextInt();
                    input.nextLine();
                    System.out.println("1.Não iniciado\n2. Em progresso\n3. Finalizado");
                    int opcaoStatus = input.nextInt();
                    input.nextLine();
                    Status status = switch (opcaoStatus) {
                        case 1 -> Status.NAO_INICIADO;
                        case 2 -> Status.EM_PROGRESSO;
                        case 3 -> Status.FINALIZADO;
                        default -> throw new IllegalArgumentException("Opção inválida.");
                    };
                    manager.marcarStatus(idMarcarStatus, status);
                    break;

                case 5:
                    System.out.println("Listando todas as tarefas...");
                    manager.listarTodos();
                    break;
                case 6:
                    System.out.println("Digite o status que deseja buscar: ");
                    int opcaoBusca = input.nextInt();
                    input.nextLine();
                    Status statusBuscado = switch (opcaoBusca) {
                        case 1 -> Status.NAO_INICIADO;
                        case 2 -> Status.EM_PROGRESSO;
                        case 3 -> Status.FINALIZADO;
                        default -> throw new IllegalArgumentException("Opção inválida");
                    };
                    System.out.println("Listando todas as tarefas por status...");
                    manager.listarPorStatus(statusBuscado);
                    break;
                case 7:
                    System.out.println("Digite o id da tarefa: ");
                    int idBuscado = input.nextInt();
                    manager.verPorId(idBuscado);
            }
            if (opcao == 0) {
                System.out.println("Encerrando programa...");
                break;
            }
        }


    }
}




