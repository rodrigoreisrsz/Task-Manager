package com.reis.test.taskManagerTest;

import com.reis.service.TaskService;
import org.junit.jupiter.api.Test;

class TaskManagerTest {

    @Test
    void deveAdicionarTarefaComSucesso() {
        // Arrange
        TaskService manager = new TaskService();

        // Act
        manager.adicionarTask("Estudar JUnit", "Praticar testes", "29/07/2026");


        // Assert
        // (aqui você precisa de um jeito de checar o resultado —
        //  dá uma olhada no que o TaskManager expõe pra você "ver" a lista);

    }}
