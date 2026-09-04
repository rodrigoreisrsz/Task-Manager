import { useEffect, useMemo, useState } from "react";
import type { Task, Status } from "./types";
import { listarTasks, criarTask, mudarStatus, deletarTask } from "./api";
import "./App.css";

const PROXIMO_STATUS: Record<Status, Status> = {
  NAO_INICIADO: "EM_PROGRESSO",
  EM_PROGRESSO: "FINALIZADO",
  FINALIZADO: "NAO_INICIADO",
};

const ROTULO_STATUS: Record<Status, string> = {
  NAO_INICIADO: "Pendente",
  EM_PROGRESSO: "Em progresso",
  FINALIZADO: "Completado",
};

type Filtro = "TODAS" | "CONCLUIDAS" | "PENDENTES";

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [data, setData] = useState("");
  const [carregando, setCarregando] = useState(true);
  const [filtro, setFiltro] = useState<Filtro>("TODAS");

  async function carregarTasks() {
    setCarregando(true);
    const lista = await listarTasks();
    setTasks(lista);
    setCarregando(false);
  }

  useEffect(() => {
    carregarTasks();
  }, []);

  async function handleCriar(e: React.FormEvent) {
    e.preventDefault();
    if (!nome.trim()) return;
    await criarTask(nome, descricao, data);
    setNome("");
    setDescricao("");
    setData("");
    carregarTasks();
  }

  async function handleAvancarStatus(task: Task) {
    await mudarStatus(task.id, PROXIMO_STATUS[task.status]);
    carregarTasks();
  }

  async function handleDeletar(id: number) {
    await deletarTask(id);
    carregarTasks();
  }

  const tasksFiltradas = useMemo(() => {
    if (filtro === "CONCLUIDAS") return tasks.filter((t) => t.status === "FINALIZADO");
    if (filtro === "PENDENTES") return tasks.filter((t) => t.status !== "FINALIZADO");
    return tasks;
  }, [tasks, filtro]);

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="sidebar-marca">
          <div className="icone">T</div>
          <div>
            <strong>Task Manager</strong>
            <span>Gestão de Tarefas</span>
          </div>
        </div>

        <div className="sidebar-secao-titulo">Navegação</div>
        <div className="sidebar-item ativo">Tarefas</div>
       
      </aside>

      <main className="conteudo">
        <div className="conteudo-topo">
          <h1>Tarefas</h1>
          <span>{tasks.length} no total</span>
        </div>

        <form className="form-nova-tarefa" onSubmit={handleCriar}>
          <input
            placeholder="Título da tarefa"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
          <input
            placeholder="Descrição"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />
          <input
            placeholder="Data (ex: 04/09/2026)"
            value={data}
            onChange={(e) => setData(e.target.value)}
          />
          <button type="submit">Adicionar</button>
        </form>

        <div className="abas">
          <button className={filtro === "TODAS" ? "ativa" : ""} onClick={() => setFiltro("TODAS")}>
            Todas
          </button>
          <button className={filtro === "CONCLUIDAS" ? "ativa" : ""} onClick={() => setFiltro("CONCLUIDAS")}>
            Completadas
          </button>
          <button className={filtro === "PENDENTES" ? "ativa" : ""} onClick={() => setFiltro("PENDENTES")}>
            Sem completar
          </button>
        </div>

        <div className="tabela-wrapper">
          {carregando ? (
            <div className="aviso-vazio">Carregando...</div>
          ) : tasksFiltradas.length === 0 ? (
            <div className="aviso-vazio">Nenhuma tarefa nessa categoria.</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Tarefa</th>
                  <th>Descrição</th>
                  <th>Data</th>
                  <th>Status</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {tasksFiltradas.map((task) => (
                  <tr key={task.id}>
                    <td><strong>{task.nome}</strong></td>
                    <td className="celula-descricao">{task.descricao}</td>
                    <td className="celula-data">{task.data || "—"}</td>
                    <td>
                      <span className={`badge badge-${task.status}`}>
                        {ROTULO_STATUS[task.status]}
                      </span>
                    </td>
                    <td>
                      <div className="acoes-tabela">
                        <button title="Avançar status" onClick={() => handleAvancarStatus(task)}>↻</button>
                        <button title="Remover" className="botao-deletar" onClick={() => handleDeletar(task.id)}>✕</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </main>
    </div>
  );
}

export default App;