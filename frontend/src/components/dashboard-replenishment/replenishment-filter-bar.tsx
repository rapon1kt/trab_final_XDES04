"use client";

interface Props {
  currentFilter: string;
  onFilterChange: (val: string) => void;
  onAdd: () => void;
}

export default function ReplenishmentFilterBar({
  currentFilter,
  onFilterChange,
  onAdd,
}: Props) {
  return (
    <div className="filter-bar-wrapper">
      <div
        className="dashboard-left-container-forms"
        style={{ marginBottom: 0 }}
      >
        <div className="dashboard-left-container-input-div">
          <p className="dashboard-left-container-label">Filtrar Status</p>
          <select
            className="dashboard-left-container-select"
            value={currentFilter}
            onChange={(e) => onFilterChange(e.target.value)}
          >
            <option value="ALL">Todos</option>
            <option value="OPEN">Abertos</option>
            <option value="COMPLETED">Concluídos</option>
            <option value="CANCELED">Cancelados</option>
          </select>
        </div>
      </div>
      <button className="add-user-button" onClick={onAdd}>
        Novo Pedido
      </button>
    </div>
  );
}
