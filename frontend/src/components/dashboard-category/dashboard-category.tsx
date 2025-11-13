"use client";
import api from "@/lib/api";
import { useState, useEffect } from "react";
import { Category } from "@/domain/model";
import "./dashboard-category.modules.css";

import CategoryFilterBar from "./category-filter-bar";
import CategoryTable from "./category-table";
import CategoryForm, { CategoryFormData } from "./category-form";
import DeleteConfirmation from "./delete-confirmation";

export interface CategoryQuery {
  filter: "NAME" | "ALL";
  value: string;
}

export default function DashboardCategory() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [categoryToDelete, setCategoryToDelete] = useState<Category | null>(
    null
  );
  const [categoryToEdit, setCategoryToEdit] = useState<Category | null>(null);
  const [isCreatingCategory, setIsCreatingCategory] = useState(false);

  const [deleteError, setDeleteError] = useState<string | null>(null);
  const [editError, setEditError] = useState<string | null>(null);
  const [createError, setCreateError] = useState<string | null>(null);

  const [editLoading, setEditLoading] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);

  const [activeQuery, setActiveQuery] = useState<CategoryQuery>({
    filter: "NAME",
    value: "",
  });

  const fetchCategories = async () => {
    let url = "/categories?filter=ALL";
    if (activeQuery.value.trim() !== "") {
      url = `/categories?filter=${activeQuery.filter}&value=${activeQuery.value}`;
    }
    try {
      setLoading(true);
      const response = await api.get(url);
      setCategories(response.data);
      setError(null);
    } catch (err: any) {
      setError(err.response?.data?.message || "Falha ao buscar categorias.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, [activeQuery]);

  const showTable = () => {
    setIsCreatingCategory(false);
    setCategoryToEdit(null);
    setCategoryToDelete(null);
  };

  const handleOpenCreateModal = () => {
    setIsCreatingCategory(true);
    setCategoryToEdit(null);
    setCategoryToDelete(null);
    setCreateError(null);
  };

  const handleOpenEditModal = (category: Category) => {
    setCategoryToEdit(category);
    setIsCreatingCategory(false);
    setCategoryToDelete(null);
    setEditError(null);
  };

  const handleOpenDeleteModal = (category: Category) => {
    setCategoryToDelete(category);
    setIsCreatingCategory(false);
    setCategoryToEdit(null);
    setDeleteError(null);
  };

  const handleSearch = (query: CategoryQuery) => {
    setActiveQuery(query);
    showTable();
  };

  const handleConfirmDelete = async () => {
    if (!categoryToDelete) return;
    setDeleteError(null);
    try {
      const response = await api.delete(
        `/categories?id=${categoryToDelete.id}`
      );
      alert(response.data);
      showTable();
      fetchCategories();
    } catch (err: any) {
      setDeleteError(
        err.response?.data?.message || "Erro ao deletar categoria."
      );
    }
  };

  const handleConfirmEdit = async (formData: CategoryFormData) => {
    if (!categoryToEdit) return;
    setEditLoading(true);
    setEditError(null);
    try {
      await api.patch(`/categories?id=${categoryToEdit.id}`, formData);
      alert("Categoria atualizada com sucesso!");
      showTable();
      fetchCategories();
    } catch (err: any) {
      setEditError(
        err.response?.data?.message || "Erro ao atualizar categoria."
      );
    } finally {
      setEditLoading(false);
    }
  };

  const handleConfirmCreate = async (formData: CategoryFormData) => {
    setCreateLoading(true);
    setCreateError(null);
    try {
      await api.post("/categories", formData);
      alert("Categoria criada com sucesso!");
      showTable();
      fetchCategories();
    } catch (err: any) {
      setCreateError(err.response?.data?.message || "Erro ao criar categoria.");
    } finally {
      setCreateLoading(false);
    }
  };

  return (
    <main className="dashboard-container">
      {!categoryToDelete && !categoryToEdit && !isCreatingCategory && (
        <div className="dashboard-left-container">
          <h1 className="dashboard-title">Categorias</h1>
          <CategoryFilterBar
            initialQuery={activeQuery}
            onSearch={handleSearch}
            onAddCategory={handleOpenCreateModal}
          />
        </div>
      )}
      {categoryToDelete ? (
        <DeleteConfirmation
          category={categoryToDelete}
          error={deleteError}
          onConfirm={handleConfirmDelete}
          onCancel={showTable}
        />
      ) : categoryToEdit ? (
        <CategoryForm
          title="Editar Categoria"
          initialData={{
            name: categoryToEdit.name,
            description: categoryToEdit.description,
          }}
          loading={editLoading}
          error={editError}
          onSubmit={handleConfirmEdit}
          onCancel={showTable}
        />
      ) : isCreatingCategory ? (
        <CategoryForm
          title="Criar Nova Categoria"
          initialData={{ name: "", description: "" }}
          loading={createLoading}
          error={createError}
          onSubmit={handleConfirmCreate}
          onCancel={showTable}
        />
      ) : (
        <CategoryTable
          categories={categories}
          loading={loading}
          error={error}
          onEditCategory={handleOpenEditModal}
          onDeleteCategory={handleOpenDeleteModal}
        />
      )}
    </main>
  );
}
