"use client";
import { useState, useEffect, FormEvent } from "react";

export type SupplierFormData = {
  name: string;
  cnpj: string;
  email: string;
  phone: string;
};

type SupplierFormProps = {
  title: string;
  initialData: SupplierFormData;
  loading: boolean;
  error: string | null;
  onSubmit: (data: SupplierFormData) => void;
  onCancel: () => void;
};

export default function SupplierForm({
  title,
  initialData,
  loading,
  error,
  onSubmit,
  onCancel,
}: SupplierFormProps) {
  const [formData, setFormData] = useState(initialData);

  useEffect(() => {
    setFormData(initialData);
  }, [initialData]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
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
          <p className="edit-form-label">Nome / Razão Social</p>
          <input
            type="text"
            name="name"
            className="edit-form-input"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="edit-form-input-div">
          <p className="edit-form-label">CNPJ</p>
          <input
            type="text"
            name="cnpj"
            className="edit-form-input"
            value={formData.cnpj}
            onChange={handleChange}
            required
          />
        </div>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Email</p>
          <input
            type="email"
            name="email"
            className="edit-form-input"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Telefone</p>
          <input
            type="text"
            name="phone"
            className="edit-form-input"
            value={formData.phone}
            onChange={handleChange}
          />
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
