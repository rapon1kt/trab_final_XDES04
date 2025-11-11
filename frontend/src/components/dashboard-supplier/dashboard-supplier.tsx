"use client";
import { useState, useEffect } from "react";
import api from "@/lib/api";

import { Supplier } from "@/domain/model";
import "./dashboard-supplier.modules.css";

import SupplierFilterBar from "./supplier-filter-bar";
import SupplierTable from "./supplier-table";
import SupplierForm, { SupplierFormData } from "./supplier-form";
import DeleteConfirmation from "./delete-confirmation";

export interface SupplierQuery {
  filter: "NAME" | "CNPJ" | "ALL";
  value: string;
}

export default function DashboardSupplier() {
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [supplierToDelete, setSupplierToDelete] = useState<Supplier | null>(
    null
  );
  const [supplierToEdit, setSupplierToEdit] = useState<Supplier | null>(null);
  const [isCreatingSupplier, setIsCreatingSupplier] = useState(false);

  const [deleteError, setDeleteError] = useState<string | null>(null);
  const [editError, setEditError] = useState<string | null>(null);
  const [createError, setCreateError] = useState<string | null>(null);

  const [editLoading, setEditLoading] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);

  const [activeQuery, setActiveQuery] = useState<SupplierQuery>({
    filter: "NAME",
    value: "",
  });

  const fetchSuppliers = async () => {
    let url = "/suppliers?filter=ALL";
    if (activeQuery.value.trim() !== "") {
      const params = new URLSearchParams({
        filter: activeQuery.filter,
        value: activeQuery.value.trim(),
      });
      url = `/suppliers?${params.toString()}`;
    }
    try {
      setLoading(true);
      const response = await api.get(url);
      setSuppliers(response.data);
      setError(null);
    } catch (err: any) {
      setError(err.response?.data?.message || "Falha ao buscar fornecedores.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSuppliers();
  }, [activeQuery]);

  const showTable = () => {
    setIsCreatingSupplier(false);
    setSupplierToEdit(null);
    setSupplierToDelete(null);
  };

  const handleOpenCreateModal = () => {
    setIsCreatingSupplier(true);
    setSupplierToEdit(null);
    setSupplierToDelete(null);
    setCreateError(null);
  };

  const handleOpenEditModal = (supplier: Supplier) => {
    setSupplierToEdit(supplier);
    setIsCreatingSupplier(false);
    setSupplierToDelete(null);
    setEditError(null);
  };

  const handleOpenDeleteModal = (supplier: Supplier) => {
    setSupplierToDelete(supplier);
    setIsCreatingSupplier(false);
    setSupplierToEdit(null);
    setDeleteError(null);
  };

  const handleSearch = (query: SupplierQuery) => {
    setActiveQuery(query);
    showTable();
  };

  const handleConfirmDelete = async () => {
    if (!supplierToDelete) return;
    setDeleteError(null);
    try {
      const response = await api.delete(`/suppliers?id=${supplierToDelete.id}`);
      alert(response.data);
      showTable();
      fetchSuppliers();
    } catch (err: any) {
      setDeleteError(
        err.response?.data?.message || "Erro ao deletar fornecedor."
      );
    }
  };

  const handleConfirmEdit = async (formData: SupplierFormData) => {
    if (!supplierToEdit) return;
    setEditLoading(true);
    setEditError(null);
    try {
      await api.put(`/suppliers?id=${supplierToEdit.id}`, formData);
      alert("Fornecedor atualizado com sucesso!");
      showTable();
      fetchSuppliers();
    } catch (err: any) {
      setEditError(
        err.response?.data?.message || "Erro ao atualizar fornecedor."
      );
    } finally {
      setEditLoading(false);
    }
  };

  const handleConfirmCreate = async (formData: SupplierFormData) => {
    setCreateLoading(true);
    setCreateError(null);
    try {
      await api.post("/suppliers", formData);
      alert("Fornecedor criado com sucesso!");
      showTable();
      fetchSuppliers();
    } catch (err: any) {
      setCreateError(
        err.response?.data?.message || "Erro ao criar fornecedor."
      );
    } finally {
      setCreateLoading(false);
    }
  };

  return (
    <main className="dashboard-container">
      {!supplierToDelete && !supplierToEdit && !isCreatingSupplier && (
        <div className="dashboard-left-container">
          <h1 className="dashboard-title">Fornecedores</h1>
          <SupplierFilterBar
            initialQuery={activeQuery}
            onSearch={handleSearch}
            onAddSupplier={handleOpenCreateModal}
          />
        </div>
      )}

      {supplierToDelete ? (
        <DeleteConfirmation
          supplier={supplierToDelete}
          error={deleteError}
          onConfirm={handleConfirmDelete}
          onCancel={showTable}
        />
      ) : supplierToEdit ? (
        <SupplierForm
          title="Editar Fornecedor"
          initialData={{
            name: supplierToEdit.enterpriseName,
            cnpj: supplierToEdit.cnpj,
            email: supplierToEdit.email,
            phone: supplierToEdit.phone,
          }}
          loading={editLoading}
          error={editError}
          onSubmit={handleConfirmEdit}
          onCancel={showTable}
        />
      ) : isCreatingSupplier ? (
        <SupplierForm
          title="Criar Novo Fornecedor"
          initialData={{ name: "", cnpj: "", email: "", phone: "" }}
          loading={createLoading}
          error={createError}
          onSubmit={handleConfirmCreate}
          onCancel={showTable}
        />
      ) : (
        <SupplierTable
          suppliers={suppliers}
          loading={loading}
          error={error}
          onEditSupplier={handleOpenEditModal}
          onDeleteSupplier={handleOpenDeleteModal}
        />
      )}
    </main>
  );
}
