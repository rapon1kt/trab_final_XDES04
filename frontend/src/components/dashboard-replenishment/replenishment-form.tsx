"use client";
import { useState, FormEvent } from "react";
import { Product } from "@/domain/model";

export interface ReplenishmentFormData {
  productId: string;
  quantity: string;
}

interface Props {
  products: Product[];
  loading: boolean;
  onSubmit: (data: ReplenishmentFormData) => void;
  onCancel: () => void;
}

export default function ReplenishmentForm({
  products,
  loading,
  onSubmit,
  onCancel,
}: Props) {
  const [formData, setFormData] = useState<ReplenishmentFormData>({
    productId: "",
    quantity: "1",
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <div className="dashboard-right-container">
      <h3 className="dashboard-title">Novo Pedido de Reposição</h3>
      <form onSubmit={handleSubmit}>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Produto</p>
          <select
            className="edit-form-select"
            value={formData.productId}
            onChange={(e) =>
              setFormData({ ...formData, productId: e.target.value })
            }
            required
          >
            <option value="">Selecione...</option>
            {products.map((p) => (
              <option key={p.id} value={p.id}>
                {p.name}
              </option>
            ))}
          </select>
        </div>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Quantidade</p>
          <input
            type="number"
            className="edit-form-input"
            value={formData.quantity}
            onChange={(e) =>
              setFormData({ ...formData, quantity: e.target.value })
            }
            min="1"
            required
          />
        </div>
        <div className="dashboard-right-container-buttons">
          <button
            type="submit"
            className="dashboard-right-container-button confirm"
            disabled={loading}
          >
            {loading ? "Criando..." : "Criar Pedido"}
          </button>
          <button
            type="button"
            className="dashboard-right-container-button cancelar"
            onClick={onCancel}
            disabled={loading}
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}
