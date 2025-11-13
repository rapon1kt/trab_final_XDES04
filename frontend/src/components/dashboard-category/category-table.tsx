"use client";
import { Category } from "@/domain/model";

interface CategoryTableProps {
  categories: Category[];
  loading: boolean;
  error: string | null;
  onEditCategory: (category: Category) => void;
  onDeleteCategory: (category: Category) => void;
}

export default function CategoryTable({
  categories,
  loading,
  error,
  onEditCategory,
  onDeleteCategory,
}: CategoryTableProps) {
  if (loading) return <p>Carregando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th style={{ width: "30%" }} className="dashboard-table-title">
            Nome
          </th>
          <th style={{ width: "50%" }} className="dashboard-table-title">
            Descrição
          </th>
          <th className="dashboard-table-title">Ações</th>
        </tr>
      </thead>
      <tbody>
        {categories.map((category) => (
          <tr className="dashboard-table-body" key={category.id}>
            <td style={{ width: "30%" }} className="dashboard-table-value">
              {category.name}
            </td>
            <td style={{ width: "50%" }} className="dashboard-table-value">
              {category.description}
            </td>
            <td className="dashboard-table-value actions">
              <button
                className="dashboard-table-button edit"
                onClick={() => onEditCategory(category)}
              >
                Editar
              </button>
              <button
                className="dashboard-table-button cancel"
                onClick={() => onDeleteCategory(category)}
              >
                Deletar
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
