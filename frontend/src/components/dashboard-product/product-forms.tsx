"use client";
import { useState, useEffect, FormEvent } from "react";
import { Category, Supplier } from "@/domain/model";

export type ProductFormData = {
  name: string;
  description: string;
  categoryId: string;
  supplierId: string;
  quantity: string;
  buyPrice: string;
  salePrice: string;
  validDate: string;
};

type ProductFormProps = {
  title: string;
  initialData: ProductFormData;
  categories: Category[];
  suppliers: Supplier[];
  loading: boolean;
  error: string | null;
  onSubmit: (data: ProductFormData) => void;
  onCancel: () => void;
};

export default function ProductForm({
  title,
  initialData,
  categories,
  suppliers,
  loading,
  error,
  onSubmit,
  onCancel,
}: ProductFormProps) {
  const [formData, setFormData] = useState(initialData);

  useEffect(() => {
    setFormData(initialData);
  }, [initialData]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <div className="dashboard-right-container">
      <h3 className="dashboard-title">{title}</h3>
      {error && <p className="dashboard-p-error">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Nome do Produto</p>
          <input
            type="text"
            name="name"
            className="edit-form-input"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-row">
          <div className="edit-form-input-div">
            <p className="edit-form-label">Categoria</p>
            <select
              name="categoryId"
              className="edit-form-select"
              value={formData.categoryId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione...</option>
              {categories.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>
          <div className="edit-form-input-div">
            <p className="edit-form-label">Fornecedor</p>
            <select
              name="supplierId"
              className="edit-form-select"
              value={formData.supplierId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione...</option>
              {suppliers.map((s) => (
                <option key={s.id} value={s.id}>
                  {s.enterpriseName}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="edit-form-input-div">
          <p className="edit-form-label">Descrição</p>
          <textarea
            name="description"
            className="edit-form-textarea"
            value={formData.description}
            onChange={handleChange}
          />
        </div>

        <div className="form-row">
          <div className="edit-form-input-div">
            <p className="edit-form-label">Quantidade (Estoque)</p>
            <input
              type="number"
              name="quantity"
              className="edit-form-input"
              value={formData.quantity}
              onChange={handleChange}
              min="0"
              step="1"
            />
          </div>
          <div className="edit-form-input-div">
            <p className="edit-form-label">Data de Validade</p>
            <input
              type="datetime-local"
              name="validDate"
              className="edit-form-input"
              value={formData.validDate}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="edit-form-input-div">
            <p className="edit-form-label">Preço de Compra (R$)</p>
            <input
              type="number"
              name="buyPrice"
              className="edit-form-input"
              value={formData.buyPrice}
              onChange={handleChange}
              min="0"
              step="0.01"
            />
          </div>
          <div className="edit-form-input-div">
            <p className="edit-form-label">Preço de Venda (R$)</p>
            <input
              type="number"
              name="salePrice"
              className="edit-form-input"
              value={formData.salePrice}
              onChange={handleChange}
              min="0"
              step="0.01"
            />
          </div>
        </div>

        <div className="dashboard-right-container-buttons">
          <button
            type="submit"
            className="dashboard-right-container-button confirm"
            disabled={loading}
          >
            {loading ? "Salvando..." : "Salvar"}
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
