"use client";
import { useState, FormEvent } from "react";
import { CategoryQuery } from "./dashboard-category";

interface CategoryFilterBarProps {
  initialQuery: CategoryQuery;
  onSearch: (query: CategoryQuery) => void;
  onAddCategory: () => void;
}

export default function CategoryFilterBar({
  initialQuery,
  onSearch,
  onAddCategory,
}: CategoryFilterBarProps) {
  const [filterType, setFilterType] = useState<CategoryQuery["filter"]>(
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
              setFilterType(e.target.value as CategoryQuery["filter"])
            }
          >
            <option value="NAME">Nome</option>
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

      <button className="add-user-button" onClick={onAddCategory}>
        Adicionar Categoria
      </button>
    </div>
  );
}
