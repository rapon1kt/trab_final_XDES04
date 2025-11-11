"use client";
import { useState, useEffect, FormEvent } from "react";

export type EditFormData = {
  name: string;
  email: string;
  password: string;
  role: string;
};

export type CreateFormData = EditFormData & {
  password?: string;
};

type UserFormProps = {
  title: string;
  mode: "create" | "edit";
  initialData: CreateFormData | EditFormData;
  loading: boolean;
  error: string | null;
  onSubmit: (data: any) => void;
  onCancel: () => void;
};

export default function UserForm({
  title,
  mode,
  initialData,
  loading,
  error,
  onSubmit,
  onCancel,
}: UserFormProps) {
  const [formData, setFormData] = useState(initialData);

  useEffect(() => {
    setFormData(initialData);
  }, [initialData]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
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
          <p className="edit-form-label">Email</p>
          <input
            type="email"
            name="email"
            className="edit-form-input"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="edit-form-input-div">
          <p className="edit-form-label">Senha</p>
          <input
            type="password"
            name="password"
            className="edit-form-input"
            onChange={handleChange}
            required
          />
        </div>
        {mode === "create" && (
          <div className="edit-form-input-div">
            <p className="edit-form-label">Senha</p>
            <input
              type="password"
              name="password"
              className="edit-form-input"
              value={(formData as CreateFormData).password}
              onChange={handleChange}
              required
            />
          </div>
        )}
        <div className="dashboard-right-container-buttons">
          <button
            type="submit"
            className="dashboard-right-container-button confirm"
            disabled={loading}
          >
            {loading
              ? mode === "create"
                ? "Criando..."
                : "Salvando..."
              : mode === "create"
              ? "Criar"
              : "Salvar"}
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
