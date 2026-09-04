export type Status = "NAO_INICIADO" | "EM_PROGRESSO" | "FINALIZADO";

export interface Task {
  id: number;
  nome: string;
  descricao: string;
  data: string;
  status: Status;
}