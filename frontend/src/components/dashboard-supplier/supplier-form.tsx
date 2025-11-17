"use client";
import { useState, useEffect, FormEvent } from "react";

export type SupplierFormData = {
  enterpriseName: string;
  cnpj: string;
  email: string;
  phone: string;
  cep: string;
  state: string;
  city: string;
  street: string;
  number: string;
  district: string;
};

type SupplierFormProps = {
  title: string;
  initialData: SupplierFormData;
  loading: boolean;
  type: "Create" | "Edit";
  error: string | null;
  onSubmit: (data: SupplierFormData) => void;
  onCancel: () => void;
};

export default function SupplierForm({
  title,
  initialData,
  loading,
  error,
  type,
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
            name="enterpriseName"
            className="edit-form-input"
            value={formData.enterpriseName}
            onChange={handleChange}
            required
          />
        </div>
        {type == "Create" && (
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
        )}
        <div className="edit-form-input-div">
          <p className="edit-form-label">Email</p>
          <input
            type="text"
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
        {type == "Create" && (
          <fieldset className="form-fieldset">
            <legend className="form-legend">Endereço</legend>
            <div className="form-row">
              <div className="edit-form-input-div">
                <p className="edit-form-label">CEP</p>
                <input
                  type="text"
                  name="cep"
                  className="edit-form-input"
                  value={formData.cep}
                  onChange={handleChange}
                />
              </div>
              <div className="edit-form-input-div">
                <p className="edit-form-label">Estado (UF)</p>
                <input
                  type="text"
                  name="state"
                  className="edit-form-input"
                  value={formData.state}
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="form-row">
              <div className="edit-form-input-div">
                <p className="edit-form-label">Cidade</p>
                <input
                  type="text"
                  name="city"
                  className="edit-form-input"
                  value={formData.city}
                  onChange={handleChange}
                />
              </div>
              <div className="edit-form-input-div">
                <p className="edit-form-label">Bairro</p>
                <input
                  type="text"
                  name="district"
                  className="edit-form-input"
                  value={formData.district}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className="form-row">
              <div className="edit-form-input-div" style={{ flex: 3 }}>
                {" "}
                <p className="edit-form-label">Rua / Logradouro</p>
                <input
                  type="text"
                  name="street"
                  className="edit-form-input"
                  value={formData.street}
                  onChange={handleChange}
                />
              </div>
              <div className="edit-form-input-div" style={{ flex: 1 }}>
                {" "}
                <p className="edit-form-label">Número</p>
                <input
                  type="text"
                  name="number"
                  className="edit-form-input"
                  value={formData.number}
                  onChange={handleChange}
                />
              </div>
            </div>
          </fieldset>
        )}
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
