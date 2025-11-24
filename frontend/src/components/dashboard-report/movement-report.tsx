"use client";
import { useEffect, useState } from "react";
import api from "@/lib/api";
import { StockMovement } from "@/domain/model";

export default function MovementsReport() {
  const [data, setData] = useState<StockMovement[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) return;

    api
      .get("/stock-movements?filter=ALL", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setData(res.data);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Carregando...</p>;

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th className="dashboard-table-title">Data</th>
          <th className="dashboard-table-title">Produto</th>
          <th className="dashboard-table-title">Tipo</th>
          <th className="dashboard-table-title">Qtd.</th>
        </tr>
      </thead>
      <tbody>
        {data.map((m) => (
          <tr className="dashboard-table-body" key={m.id}>
            <td className="dashboard-table-value">
              {new Date(m.date).toLocaleString()}
            </td>
            <td className="dashboard-table-value">{m.productName}</td>
            <td
              className="dashboard-table-value"
              style={{
                color: m.type === "IN" ? "var(--success)" : "var(--error)",
              }}
            >
              {m.type}
            </td>
            <td className="dashboard-table-value">{m.quantity}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
