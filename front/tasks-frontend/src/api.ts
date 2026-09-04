import type { Task, Status } from "./types";

const BASE_URL = "http://localhost:8080/tasks";

export async function listarTasks(): Promise<Task[]> {
  const res = await fetch(BASE_URL);
  return res.json();
}

export async function criarTask(nome: string, descricao: string, data: string): Promise<Task> {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, descricao, data }),
  });
  return res.json();
}

export async function mudarStatus(id: number, status: Status): Promise<Task> {
  const res = await fetch(`${BASE_URL}/${id}/status`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ status }),
  });
  return res.json();
}

export async function deletarTask(id: number): Promise<void> {
  await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
}