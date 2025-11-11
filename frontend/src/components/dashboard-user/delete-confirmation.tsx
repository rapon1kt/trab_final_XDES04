"use client";
import { useState } from "react";
import { User } from "@/domain/model";

interface DeleteConfirmationProps {
  user: User;
  error: string | null;
  setDeleteError: (error: string | null) => void;
  onConfirm: (password: string) => void;
  onCancel: () => void;
}

export default function DeleteConfirmation({
  user,
  error,
  setDeleteError,
  onConfirm,
  onCancel,
}: DeleteConfirmationProps) {
  const [adminPassword, setAdminPassword] = useState("");

  const handleConfirmClick = () => {
    if (!adminPassword) {
      setDeleteError("A senha do administrador é obrigatória.");
      return;
    }
    onConfirm(adminPassword);
  };

  return (
    <div className="dashboard-right-container">
      <h3 className="dashboard-title">Confirmar Exclusão</h3>
      {error && <p className="dashboard-p-error">{error}</p>}
      <div className="dashboard-right-container-middle">
        <p className="dashboard-right-container-alert">
          Para excluir o usuário{" "}
          <strong className="user-name">{user.name}</strong> é preciso informar
          sua senha!
        </p>
        <div className="dashboard-right-container-input-div">
          <p className="dashboard-right-container-input-label">Senha</p>
          <input
            type="password"
            className="dashboard-right-container-input"
            value={adminPassword}
            onChange={(e) => {
              setAdminPassword(e.target.value);
              if (error) setDeleteError(null);
            }}
            autoFocus
          />
        </div>
      </div>
      <div className="dashboard-right-container-buttons">
        <button
          className="dashboard-right-container-button excluir"
          onClick={handleConfirmClick}
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
