"use client";
import { useState, useEffect } from "react";
import api from "@/lib/api";
import { StockMovement, Product } from "@/domain/model";
import "./dashboard-movement.modules.css";

import MovementTable from "./movement-table";
import MovementForm, { MovementFormData } from "./movement-form";
import MovementFilterBar, { MovementQuery } from "./movement-filter-bar";

export default function DashboardMovement() {
  const [movements, setMovements] = useState<StockMovement[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [isCreating, setIsCreating] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);

  const [activeQuery, setActiveQuery] = useState<MovementQuery>({
    filter: "ALL",
    value: "",
  });

  const fetchData = async () => {
    try {
      setLoading(true);
      let urlMovements = "/stock-movements?filter=ALL";
      if (activeQuery.filter !== "ALL") {
        const params = new URLSearchParams({
          filter: activeQuery.filter,
          value: activeQuery.value,
        });
        urlMovements = `/stock-movements?${params.toString()}`;
      }

      const [movRes, prodRes] = await Promise.all([
        api.get(urlMovements),
        api.get("/products?filter=ALL"),
      ]);

      setMovements(movRes.data);
      setProducts(prodRes.data);
      setError(null);
    } catch (err: any) {
      setError(err.response?.data?.message || "Erro ao carregar dados.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [activeQuery]);

  const handleCreateMovement = async (data: MovementFormData) => {
    try {
      setCreateLoading(true);
      const payload = {
        productId: data.productId,
        type: data.type,
        quantity: parseInt(data.quantity),
        reason: data.reason,
        date: new Date().toISOString(),
      };

      await api.post("/stock-movements", payload);
      alert("Movimentação registrada com sucesso!");
      setIsCreating(false);
      fetchData();
    } catch (err: any) {
      alert(err.response?.data?.message || "Erro ao registrar movimentação.");
    } finally {
      setCreateLoading(false);
    }
  };

  return (
    <main className="dashboard-container">
      {!isCreating && (
        <div className="dashboard-left-container">
          <h1 className="dashboard-title">Movimentação de Estoque</h1>
          <MovementFilterBar
            onSearch={setActiveQuery}
            onAdd={() => setIsCreating(true)}
          />
        </div>
      )}

      {isCreating ? (
        <MovementForm
          products={products}
          loading={createLoading}
          onSubmit={handleCreateMovement}
          onCancel={() => setIsCreating(false)}
        />
      ) : (
        <MovementTable movements={movements} loading={loading} error={error} />
      )}
    </main>
  );
}
