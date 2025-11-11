"use client";
import { Supplier } from "@/domain/model";

interface DeleteConfirmationProps {
  supplier: Supplier;
  error: string | null;
  onConfirm: () => void;
  onCancel: () => void;
}

export default function DeleteConfirmation({
  supplier,
  error,
  onConfirm,
  onCancel,
}: DeleteConfirmationProps) {
  return (
    <div className="dashboard-right-container">
      <h3 className="dashboard-title">Confirmar Exclusão</h3>
      {error && <p className="dashboard-p-error">{error}</p>}
      <div className="dashboard-right-container-middle">
        <p className="dashboard-right-container-alert">
          Tem certeza que deseja excluir o fornecedor{" "}
          <strong className="user-name">{supplier.enterpriseName}</strong>?
        </p>
        <p className="dashboard-right-container-alert">
          Esta ação não pode ser desfeita.
        </p>
      </div>
      <div className="dashboard-right-container-buttons">
        <button
          className="dashboard-right-container-button excluir"
          onClick={onConfirm}
        >
          Excluir
        </button>
        <button
          className="dashboard-right-container-button cancelar"
          onClick={onCancel}
        >
          Cancelar
        </button>
      </div>
    </div>
  );
}
