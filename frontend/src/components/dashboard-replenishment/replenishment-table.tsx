"use client";
import { Replenishment } from "@/domain/model";

interface Props {
  requests: Replenishment[];
  loading: boolean;
  error: string | null;
  onUpdateStatus: (id: string, status: "COMPLETED" | "CANCELED") => void;
}

export default function ReplenishmentTable({
  requests,
  loading,
  error,
  onUpdateStatus,
}: Props) {
  if (loading) return <p>Carregando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  const getStatusColor = (status: string) => {
    if (status === "OPEN") return "var(--text-primary)"; // Branco/Padrão
    if (status === "COMPLETED") return "var(--success)"; // Verde
    return "var(--error)"; // Vermelho
  };

  const translateStatus = (status: string) => {
    if (status === "OPEN") return "ABERTO";
    if (status === "COMPLETED") return "CONCLUÍDO";
    return "CANCELADO";
  };

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th className="dashboard-table-title">Data</th>
          <th className="dashboard-table-title">Produto</th>
          <th className="dashboard-table-title">Fornecedor</th>
          <th className="dashboard-table-title">Qtd.</th>
          <th className="dashboard-table-title">Status</th>
          <th className="dashboard-table-title">Ações</th>
        </tr>
      </thead>
      <tbody>
        {requests.map((req) => (
          <tr className="dashboard-table-body" key={req.id}>
            <td className="dashboard-table-value">
              {new Date(req.createdAt).toLocaleDateString()}
            </td>
            <td className="dashboard-table-value">{req.productName}</td>
            <td className="dashboard-table-value">{req.supplierName}</td>
            <td className="dashboard-table-value">{req.quantity}</td>
            <td
              className="dashboard-table-value"
              style={{ color: getStatusColor(req.status), fontWeight: "bold" }}
            >
              {translateStatus(req.status)}
            </td>
            <td className="dashboard-table-value actions">
              {req.status === "OPEN" && (
                <>
                  <button
                    className="dashboard-table-button edit" // Reusando classe verde
                    onClick={() => onUpdateStatus(req.id, "COMPLETED")}
                    title="Receber Mercadoria"
                  >
                    Receber
                  </button>
                  <button
                    className="dashboard-table-button cancel"
                    onClick={() => onUpdateStatus(req.id, "CANCELED")}
                  >
                    Cancelar
                  </button>
                </>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
