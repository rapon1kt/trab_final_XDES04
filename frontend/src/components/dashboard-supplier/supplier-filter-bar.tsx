"use client";
import { useState, FormEvent } from "react";
import { SupplierQuery } from "./dashboard-supplier";

interface SupplierFilterBarProps {
  initialQuery: SupplierQuery;
  onSearch: (query: SupplierQuery) => void;
  onAddSupplier: () => void;
}

export default function SupplierFilterBar({
  initialQuery,
  onSearch,
  onAddSupplier,
}: SupplierFilterBarProps) {
  const [filterType, setFilterType] = useState<SupplierQuery["filter"]>(
    initialQuery.filter
  );
  const [filterValue, setFilterValue] = useState(initialQuery.value);

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSearch({ filter: filterType, value: filterValue });
  };

  const handleClear = () => {
    setFilterType("NAME");
    setFilterValue("");
    onSearch({ filter: "NAME", value: "" });
  };

  return (
    <div className="filter-bar-wrapper">
      <form className="dashboard-left-container-forms" onSubmit={handleSubmit}>
        <div className="dashboard-left-container-input-div">
          <p className="dashboard-left-container-label">Filtrar</p>
          <select
            id="filterType"
            className="dashboard-left-container-select"
            value={filterType}
            onChange={(e) =>
              setFilterType(e.target.value as SupplierQuery["filter"])
            }
          >
            <option value="NAME">Nome</option>
            <option value="CNPJ">CNPJ</option>
          </select>
        </div>
        <div className="dashboard-left-container-input-div">
          <p className="dashboard-left-container-label">Valor</p>
          <input
            id="filterValue"
            type="text"
            value={filterValue}
            onChange={(e) => setFilterValue(e.target.value)}
            placeholder="Digite para buscar..."
          />
        </div>
        <button type="submit">Buscar</button>
        <button type="button" onClick={handleClear}>
          Limpar
        </button>
      </form>

      <button className="add-user-button" onClick={onAddSupplier}>
        Adicionar Fornecedor
      </button>
    </div>
  );
}
