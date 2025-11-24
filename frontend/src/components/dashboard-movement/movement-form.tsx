"use client";
import { useState, FormEvent } from "react";
import { Product } from "@/domain/model";

export interface MovementFormData {
  productId: string;
  type: "IN" | "OUT";
  quantity: string;
  reason: string;
}

interface Props {
  products: Product[];
  loading: boolean;
  onSubmit: (data: MovementFormData) => void;
  onCancel: () => void;
}

export default function MovementForm({
  products,
  loading,
  onSubmit,
  onCancel,
}: Props) {
  const [formData, setFormData] = useState<MovementFormData>({
    productId: "",
    type: "IN",
    quantity: "1",
    reason: "",
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const handleChange = (e: any) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div className="dashboard-right-container">
      <h3 className="dashboard-title">Registrar Movimentação</h3>
      <form onSubmit={handleSubmit}>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Produto</p>
          <select
            name="productId"
            className="edit-form-select"
            value={formData.productId}
            onChange={handleChange}
            required
          >
            <option value="">Selecione o Produto...</option>
            {products.map((p) => (
              <option key={p.id} value={p.id}>
                {p.name} (Atual: {p.quantity})
              </option>
            ))}
          </select>
        </div>

        <div className="form-row">
          <div className="edit-form-input-div">
            <p className="edit-form-label">Tipo</p>
            <select
              name="type"
              className="edit-form-select"
              value={formData.type}
              onChange={handleChange}
            >
              <option value="IN">Entrada (Compra/Devolução)</option>
              <option value="OUT">Saída (Venda/Perda)</option>
            </select>
          </div>
          <div className="edit-form-input-div">
            <p className="edit-form-label">Quantidade</p>
            <input
              type="number"
              name="quantity"
              className="edit-form-input"
              value={formData.quantity}
              onChange={handleChange}
              min="1"
              required
            />
          </div>
        </div>

        <div className="edit-form-input-div">
          <p className="edit-form-label">Motivo / Observação</p>
          <input
            type="text"
            name="reason"
            className="edit-form-input"
            value={formData.reason}
            onChange={handleChange}
            placeholder="Ex: Reposição mensal, Venda #123"
            required
          />
        </div>

        <div className="dashboard-right-container-buttons">
          <button
            type="submit"
            className="dashboard-right-container-button confirm"
            disabled={loading}
          >
            {loading ? "Registrando..." : "Registrar"}
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
