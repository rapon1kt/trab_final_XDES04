"use client";
import { useState, useEffect, FormEvent } from "react";
import api from "@/lib/api";
import { User } from "@/domain/model";
import "./dashboard-user.modules.css";
import UserFilterBar from "./user-filter-bar";
import UserTable from "./user-table";
import UserForm, { CreateFormData, EditFormData } from "./user-forms";
import DeleteConfirmation from "./delete-confirmation";

export interface UserQuery {
  filter: "NAME" | "EMAIL" | "ROLE" | "ALL";
  value: string;
}

export default function DashboardUser() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [userToDelete, setUserToDelete] = useState<User | null>(null);
  const [userToEdit, setUserToEdit] = useState<User | null>(null);
  const [isCreatingUser, setIsCreatingUser] = useState(false);

  const [deleteError, setDeleteError] = useState<string | null>(null);
  const [editError, setEditError] = useState<string | null>(null);
  const [createError, setCreateError] = useState<string | null>(null);

  const [editLoading, setEditLoading] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);

  const [activeQuery, setActiveQuery] = useState<UserQuery>({
    filter: "NAME",
    value: "",
  });

  const fetchUsers = async () => {
    let url = "/users?filter=ALL";
    if (activeQuery.value.trim() !== "") {
      const params = new URLSearchParams({
        filter: activeQuery.filter,
        value: activeQuery.value.trim(),
      });
      url = `/users?${params.toString()}`;
    }
    try {
      setLoading(true);
      const response = await api.get(url);
      setUsers(response.data);
      setError(null);
    } catch (err: any) {
      setError(err.response?.data?.message || "Falha ao buscar usuários.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, [activeQuery]);

  const showTable = () => {
    setIsCreatingUser(false);
    setUserToEdit(null);
    setUserToDelete(null);
  };

  const handleOpenCreateModal = () => {
    setIsCreatingUser(true);
    setUserToEdit(null);
    setUserToDelete(null);
    setCreateError(null);
  };

  const handleOpenEditModal = (user: User) => {
    setUserToEdit(user);
    setIsCreatingUser(false);
    setUserToDelete(null);
    setEditError(null);
  };

  const handleOpenDeleteModal = (user: User) => {
    setUserToDelete(user);
    setIsCreatingUser(false);
    setUserToEdit(null);
    setDeleteError(null);
  };

  const handleSearch = (query: UserQuery) => {
    setActiveQuery(query);
    showTable();
  };

  const handleConfirmDelete = async (password: string) => {
    if (!userToDelete) return;
    setDeleteError(null);
    try {
      const response = await api.delete(`/users?id=${userToDelete.id}`, {
        data: { password: password },
      });
      alert(response.data);
      showTable();
      fetchUsers();
    } catch (err: any) {
      setDeleteError(err.response?.data?.message || "Erro ao deletar usuário.");
    }
  };

  const handleConfirmEdit = async (formData: EditFormData) => {
    if (!userToEdit) return;
    setEditLoading(true);
    setEditError(null);
    try {
      await api.patch(`/users?id=${userToEdit.id}`, formData);
      alert("Usuário atualizado com sucesso!");
      showTable();
      fetchUsers();
    } catch (err: any) {
      setEditError(err.response?.data?.message || "Erro ao atualizar usuário.");
    } finally {
      setEditLoading(false);
    }
  };

  const handleConfirmCreate = async (formData: CreateFormData) => {
    setCreateLoading(true);
    setCreateError(null);
    try {
      await api.post("/users", formData);
      alert("Usuário criado com sucesso!");
      showTable();
      fetchUsers();
    } catch (err: any) {
      setCreateError(err.response?.data?.message || "Erro ao criar usuário.");
    } finally {
      setCreateLoading(false);
    }
  };

  return (
    <main className="dashboard-container">
      {userToDelete ? (
        <DeleteConfirmation
          user={userToDelete}
          error={deleteError}
          setDeleteError={setDeleteError}
          onConfirm={handleConfirmDelete}
          onCancel={showTable}
        />
      ) : userToEdit ? (
        <UserForm
          title="Editar Usuário"
          mode="edit"
          initialData={{
            name: "",
            email: userToEdit.email,
            password: userToEdit.password,
            role: "",
          }}
          loading={editLoading}
          error={editError}
          onSubmit={handleConfirmEdit}
          onCancel={showTable}
        />
      ) : isCreatingUser ? (
        <UserForm
          title="Criar Novo Usuário"
          mode="create"
          initialData={{ name: "", email: "", password: "", role: "USER" }}
          loading={createLoading}
          error={createError}
          onSubmit={handleConfirmCreate}
          onCancel={showTable}
        />
      ) : (
        <>
          <div className="dashboard-left-container">
            <h1 className="dashboard-title">Usuários</h1>
            <UserFilterBar
              initialQuery={activeQuery}
              onSearch={handleSearch}
              onAddUser={handleOpenCreateModal}
            />
          </div>
          <UserTable
            users={users}
            loading={loading}
            error={error}
            onEditUser={handleOpenEditModal}
            onDeleteUser={handleOpenDeleteModal}
          />
        </>
      )}
    </main>
  );
}
