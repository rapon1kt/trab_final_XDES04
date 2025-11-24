"use client";
import { useEffect, useState } from "react";
import api from "@/lib/api";

interface ReportData {
  categoryName: string;
  totalQuantity: number;
  totalValue: number;
}

export default function StockByCategoryReport() {
  const [data, setData] = useState<ReportData[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get("/reports/stock-by-category").then((res) => {
      setData(res.data);
      setLoading(false);
    });
  }, []);

  if (loading) return <p>Gerando relatório...</p>;

  // Calcula totais gerais
  const totalGeneralQty = data.reduce(
    (acc, item) => acc + item.totalQuantity,
    0
  );
  const totalGeneralVal = data.reduce((acc, item) => acc + item.totalValue, 0);

  return (
    <div style={{ width: "100%" }}>
      <h3
        className="dashboard-title"
        style={{ fontSize: "1.2rem", marginTop: "1rem" }}
      >
        Resumo de Estoque por Categoria
      </h3>
      <table className="dashboard-table">
        <thead>
          <tr className="dashboard-table-header">
            <th className="dashboard-table-title">Categoria</th>
            <th className="dashboard-table-title">Qtd. Total em Estoque</th>
            <th className="dashboard-table-title">Valor Estimado (Custo)</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr className="dashboard-table-body" key={index}>
              <td className="dashboard-table-value">{item.categoryName}</td>
              <td className="dashboard-table-value">{item.totalQuantity}</td>
              <td className="dashboard-table-value">
                {item.totalValue.toLocaleString("pt-BR", {
                  style: "currency",
                  currency: "BRL",
                })}
              </td>
            </tr>
          ))}
          {/* Linha de Totais */}
          <tr
            className="dashboard-table-body"
            style={{
              backgroundColor: "rgba(127, 255, 212, 0.05)",
              fontWeight: "bold",
            }}
          >
            <td className="dashboard-table-value">TOTAL GERAL</td>
            <td className="dashboard-table-value">{totalGeneralQty}</td>
            <td className="dashboard-table-value">
              {totalGeneralVal.toLocaleString("pt-BR", {
                style: "currency",
                currency: "BRL",
              })}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
