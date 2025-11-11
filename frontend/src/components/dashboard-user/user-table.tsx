"use client";
import { User } from "@/domain/model";

interface UserTableProps {
  users: User[];
  loading: boolean;
  error: string | null;
  onEditUser: (user: User) => void;
  onDeleteUser: (user: User) => void;
}

export default function UserTable({
  users,
  loading,
  error,
  onEditUser,
  onDeleteUser,
}: UserTableProps) {
  if (loading) return <p>Carregando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th style={{ width: "10rem" }} className="dashboard-table-title">
            Nome
          </th>
          <th style={{ width: "25rem" }} className="dashboard-table-title">
            Email
          </th>
          <th style={{ width: "7rem" }} className="dashboard-table-title">
            Role
          </th>
          <th className="dashboard-table-title">Ações</th>
        </tr>
      </thead>
      <tbody>
        {users.map((user) => (
          <tr className="dashboard-table-body" key={user.id}>
            <td style={{ width: "10rem" }} className="dashboard-table-value">
              {user.name}
            </td>
            <td style={{ width: "25rem" }} className="dashboard-table-value">
              {user.email}
            </td>
            <td style={{ width: "7rem" }} className="dashboard-table-value">
              {user.role}
            </td>
            <td className="dashboard-table-value actions">
              <button
                className="dashboard-table-button edit"
                onClick={() => onEditUser(user)}
              >
                Editar
              </button>
              <button
                className="dashboard-table-button cancel"
                onClick={() => onDeleteUser(user)}
                disabled={user.role === "ADMIN"}
              >
                Deletar
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
