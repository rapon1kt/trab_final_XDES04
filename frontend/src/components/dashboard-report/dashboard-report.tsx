"use client";
import { useState } from "react";
import "./dashboard-report.modules.css";
import StockByCategoryReport from "./stock-by-category-report";
import MovementsReport from "./movement-report";

type ReportType = "CATEGORY" | "MOVEMENTS";

export default function DashboardReport() {
  const [activeTab, setActiveTab] = useState<ReportType>("CATEGORY");

  return (
    <main className="dashboard-container">
      <div className="dashboard-left-container">
        <h1 className="dashboard-title">Relatórios Gerenciais</h1>

        {/* Abas de Navegação */}
        <div className="report-tabs">
          <button
            className={`report-tab ${activeTab === "CATEGORY" ? "active" : ""}`}
            onClick={() => setActiveTab("CATEGORY")}
          >
            Estoque por Categoria
          </button>
          <button
            className={`report-tab ${
              activeTab === "MOVEMENTS" ? "active" : ""
            }`}
            onClick={() => setActiveTab("MOVEMENTS")}
          >
            Movimentações
          </button>
        </div>

        {/* Renderização Condicional do Relatório */}
        <div className="report-content">
          {activeTab === "CATEGORY" ? (
            <StockByCategoryReport />
          ) : (
            <MovementsReport />
          )}
        </div>
      </div>
    </main>
  );
}
