"use client";
import { useState, FormEvent } from "react";

export interface MovementQuery {
  filter: "TYPE" | "ALL";
  value: string;
}

interface Props {
  onSearch: (query: MovementQuery) => void;
  onAdd: () => void;
}

export default function MovementFilterBar({ onSearch, onAdd }: Props) {
  const [filterType, setFilterType] = useState<"ALL" | "IN" | "OUT">("ALL");

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (filterType === "ALL") {
      onSearch({ filter: "ALL", value: "" });
    } else {
      onSearch({ filter: "TYPE", value: filterType });
    }
  };

  return (
    <div className="filter-bar-wrapper">
      <form className="dashboard-left-container-forms" onSubmit={handleSubmit}>
        <div className="dashboard-left-container-input-div">
          <p className="dashboard-left-container-label">Tipo de Movimento</p>
          <select
            className="dashboard-left-container-select"
            value={filterType}
            onChange={(e) => setFilterType(e.target.value as any)}
          >
            <option value="ALL">Todos</option>
            <option value="IN">Entrada</option>
            <option value="OUT">Saída</option>
          </select>
        </div>
        <button type="submit">Filtrar</button>
      </form>

      <button className="add-user-button" onClick={onAdd}>
        Nova Movimentação
      </button>
    </div>
  );
}
