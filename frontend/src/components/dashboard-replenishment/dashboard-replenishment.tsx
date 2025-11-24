"use client";
import { useState, useEffect } from "react";
import api from "@/lib/api";
import { Replenishment, Product } from "@/domain/model";
import "./dashboard-replenishment.modules.css";

import ReplenishmentTable from "./replenishment-table";
import ReplenishmentForm, { ReplenishmentFormData } from "./replenishment-form";
import ReplenishmentFilterBar from "./replenishment-filter-bar";

export default function DashboardReplenishment() {
  const [requests, setRequests] = useState<Replenishment[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [isCreating, setIsCreating] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);
  const [statusFilter, setStatusFilter] = useState<string>("ALL");

  const fetchData = async () => {
    try {
      setLoading(true);
      let url = "/replenishments?filter=ALL";
      if (statusFilter !== "ALL") {
        url = `/replenishments?filter=STATUS&value=${statusFilter}`;
      }

      const [reqRes, prodRes] = await Promise.all([
        api.get(url),
        api.get("/products?filter=ALL"),
      ]);

      setRequests(reqRes.data);
      setProducts(prodRes.data);
      setError(null);
    } catch (err: any) {
      setError("Erro ao carregar dados.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [statusFilter]);

  const handleCreate = async (data: ReplenishmentFormData) => {
    try {
      setCreateLoading(true);
      await api.post("/replenishments", {
        productId: data.productId,
        quantity: parseInt(data.quantity),
      });
      alert("Pedido criado!");
      setIsCreating(false);
      fetchData();
    } catch (err) {
      alert("Erro ao criar pedido.");
    } finally {
      setCreateLoading(false);
    }
  };

  const handleUpdateStatus = async (
    id: string,
    status: "COMPLETED" | "CANCELED"
  ) => {
    if (!confirm(`Deseja marcar este pedido como ${status}?`)) return;

    try {
      await api.patch(`/replenishments/${id}/status`, { status });
      alert("Status atualizado!");
      fetchData();
    } catch (err) {
      alert("Erro ao atualizar status.");
    }
  };

  return (
    <main className="dashboard-container">
      {!isCreating && (
        <div className="dashboard-left-container">
          <h1 className="dashboard-title">Pedidos de Reposição</h1>
          <ReplenishmentFilterBar
            currentFilter={statusFilter}
            onFilterChange={setStatusFilter}
            onAdd={() => setIsCreating(true)}
          />
        </div>
      )}

      {isCreating ? (
        <ReplenishmentForm
          products={products}
          loading={createLoading}
          onSubmit={handleCreate}
          onCancel={() => setIsCreating(false)}
        />
      ) : (
        <ReplenishmentTable
          requests={requests}
          loading={loading}
          error={error}
          onUpdateStatus={handleUpdateStatus}
        />
      )}
    </main>
  );
}
