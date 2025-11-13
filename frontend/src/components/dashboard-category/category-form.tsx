"use client";
import { useState, useEffect, FormEvent } from "react";

export type CategoryFormData = {
  name: string;
  description: string;
};

type CategoryFormProps = {
  title: string;
  initialData: CategoryFormData;
  loading: boolean;
  error: string | null;
  onSubmit: (data: CategoryFormData) => void;
  onCancel: () => void;
};

export default function CategoryForm({
  title,
  initialData,
  loading,
  error,
  onSubmit,
  onCancel,
}: CategoryFormProps) {
  const [formData, setFormData] = useState(initialData);

  useEffect(() => {
    setFormData(initialData);
  }, [initialData]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
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
          <p className="edit-form-label">Nome</p>
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
          <p className="edit-form-label">Descrição</p>
          <textarea
            name="description"
            className="edit-form-textarea"
            value={formData.description}
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
