"use client";
import { Supplier } from "@/domain/model";

interface SupplierTableProps {
  suppliers: Supplier[];
  loading: boolean;
  error: string | null;
  onEditSupplier: (supplier: Supplier) => void;
  onDeleteSupplier: (supplier: Supplier) => void;
}

export default function SupplierTable({
  suppliers,
  loading,
  error,
  onEditSupplier,
  onDeleteSupplier,
}: SupplierTableProps) {
  if (loading) return <p>Carregando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th style={{ width: "20rem" }} className="dashboard-table-title">
            Nome
          </th>
          <th style={{ width: "12rem" }} className="dashboard-table-title">
            CNPJ
          </th>
          <th style={{ width: "20rem" }} className="dashboard-table-title">
            Email
          </th>
          <th style={{ width: "10rem" }} className="dashboard-table-title">
            Telefone
          </th>
          <th className="dashboard-table-title">Ações</th>
        </tr>
      </thead>
      <tbody>
        {suppliers.map((supplier) => (
          <tr className="dashboard-table-body" key={supplier.id}>
            <td style={{ width: "20rem" }} className="dashboard-table-value">
              {supplier.enterpriseName}
            </td>
            <td style={{ width: "12rem" }} className="dashboard-table-value">
              {supplier.cnpj}
            </td>
            <td style={{ width: "20rem" }} className="dashboard-table-value">
              {supplier.email}
            </td>
            <td style={{ width: "10rem" }} className="dashboard-table-value">
              {supplier.phone}
            </td>
            <td className="dashboard-table-value actions">
              <button
                className="dashboard-table-button edit"
                onClick={() => onEditSupplier(supplier)}
              >
                Editar
              </button>
              <button
                className="dashboard-table-button cancel"
                onClick={() => onDeleteSupplier(supplier)}
                // Note que não há 'disabled' aqui
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
