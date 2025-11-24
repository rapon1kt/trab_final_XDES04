"use client";
import { StockMovement } from "@/domain/model";

interface Props {
  movements: StockMovement[];
  loading: boolean;
  error: string | null;
}

export default function MovementTable({ movements, loading, error }: Props) {
  if (loading) return <p>Carregando histórico...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  const formatDate = (dateStr: string) => {
    return new Date(dateStr).toLocaleString("pt-BR");
  };

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th className="dashboard-table-title">Data</th>
          <th className="dashboard-table-title">Produto</th>
          <th className="dashboard-table-title">Tipo</th>
          <th className="dashboard-table-title">Qtd.</th>
          <th className="dashboard-table-title">Motivo</th>
        </tr>
      </thead>
      <tbody>
        {movements.map((mov) => (
          <tr className="dashboard-table-body" key={mov.id}>
            <td className="dashboard-table-value">{formatDate(mov.date)}</td>
            <td className="dashboard-table-value">
              {mov.productName || "Produto ID " + mov.productId}
            </td>
            <td className="dashboard-table-value">
              <span
                style={{
                  color: mov.type === "IN" ? "var(--success)" : "var(--error)",
                  fontWeight: "bold",
                }}
              >
                {mov.type === "IN" ? "ENTRADA" : "SAÍDA"}
              </span>
            </td>
            <td className="dashboard-table-value">{mov.quantity}</td>
            <td className="dashboard-table-value">{mov.reason}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
