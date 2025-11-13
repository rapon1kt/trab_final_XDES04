"use client";
import api from "@/lib/api";
import { useState, useEffect } from "react";
import { Product, Category, Supplier } from "@/domain/model";
import "./dashboard-product.modules.css";

import ProductFilterBar from "./product-filter-bar";
import ProductTable from "./product-table";
import ProductForm, { ProductFormData } from "./product-forms";
import DeleteConfirmation from "./delete-confirmation";

export interface ProductQuery {
  filter: "NAME" | "ALL";
  value: string;
}

const formatInstantToInput = (instantStr: string | undefined): string => {
  if (!instantStr) return "";
  return instantStr.substring(0, 16);
};

export default function DashboardProduct() {
  const [products, setProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [productToDelete, setProductToDelete] = useState<Product | null>(null);
  const [productToEdit, setProductToEdit] = useState<Product | null>(null);
  const [isCreatingProduct, setIsCreatingProduct] = useState(false);

  const [deleteError, setDeleteError] = useState<string | null>(null);
  const [editError, setEditError] = useState<string | null>(null);
  const [createError, setCreateError] = useState<string | null>(null);

  const [editLoading, setEditLoading] = useState(false);
  const [createLoading, setCreateLoading] = useState(false);

  const [activeQuery, setActiveQuery] = useState<ProductQuery>({
    filter: "NAME",
    value: "",
  });

  const fetchProducts = async () => {
    let url = "/products?filter=ALL";
    if (activeQuery.value.trim() !== "") {
      url = `/products?filter=${activeQuery.filter}&value=${activeQuery.value}`;
    }
    try {
      setLoading(true);
      const [prodRes, catRes, supRes] = await Promise.all([
        api.get(url),
        api.get("/categories?filter=ALL"),
        api.get("/suppliers?filter=ALL"),
      ]);
      setProducts(prodRes.data);
      setCategories(catRes.data);
      setSuppliers(supRes.data);
      setError(null);
    } catch (err: any) {
      setError(err.response?.data?.message || "Falha ao buscar dados.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [activeQuery]);

  const showTable = () => {
    setIsCreatingProduct(false);
    setProductToEdit(null);
    setProductToDelete(null);
  };

  const handleOpenCreateModal = () => {
    setIsCreatingProduct(true);
    setProductToEdit(null);
    setProductToDelete(null);
    setCreateError(null);
  };

  const handleOpenEditModal = (product: Product) => {
    setProductToEdit(product);
    setIsCreatingProduct(false);
    setProductToDelete(null);
    setEditError(null);
  };

  const handleOpenDeleteModal = (product: Product) => {
    setProductToDelete(product);
    setIsCreatingProduct(false);
    setProductToEdit(null);
    setDeleteError(null);
  };

  const handleSearch = (query: ProductQuery) => {
    setActiveQuery(query);
    showTable();
  };

  const formatDataForApi = (formData: ProductFormData) => {
    return {
      ...formData,
      validDate: formData.validDate ? `${formData.validDate}:00Z` : null,
      buyPrice: parseFloat(formData.buyPrice) || 0,
      salePrice: parseFloat(formData.salePrice) || 0,
      quantity: parseInt(formData.quantity) || 0,
    };
  };

  const handleConfirmDelete = async () => {
    if (!productToDelete) return;
    setDeleteError(null);
    try {
      const response = await api.delete(`/products?id=${productToDelete.id}`);
      alert(response.data);
      showTable();
      fetchProducts();
    } catch (err: any) {
      setDeleteError(err.response?.data?.message || "Erro ao deletar produto.");
    }
  };

  const handleConfirmEdit = async (formData: ProductFormData) => {
    if (!productToEdit) return;
    setEditLoading(true);
    setEditError(null);
    try {
      const apiData = formatDataForApi(formData);
      await api.put(`/products?id=${productToEdit.id}`, apiData);
      alert("Produto atualizado com sucesso!");
      showTable();
      fetchProducts();
    } catch (err: any) {
      setEditError(err.response?.data?.message || "Erro ao atualizar produto.");
    } finally {
      setEditLoading(false);
    }
  };

  const handleConfirmCreate = async (formData: ProductFormData) => {
    setCreateLoading(true);
    setCreateError(null);
    try {
      const apiData = formatDataForApi(formData);
      await api.post("/products", apiData);
      alert("Produto criado com sucesso!");
      showTable();
      fetchProducts();
    } catch (err: any) {
      setCreateError(err.response?.data?.message || "Erro ao criar produto.");
    } finally {
      setCreateLoading(false);
    }
  };

  return (
    <main className="dashboard-container">
      {!productToDelete && !productToEdit && !isCreatingProduct && (
        <div className="dashboard-left-container">
          <h1 className="dashboard-title">Produtos</h1>
          <ProductFilterBar
            initialQuery={activeQuery}
            onSearch={handleSearch}
            onAddProduct={handleOpenCreateModal}
          />
        </div>
      )}
      {productToDelete ? (
        <DeleteConfirmation
          product={productToDelete}
          error={deleteError}
          onConfirm={handleConfirmDelete}
          onCancel={showTable}
        />
      ) : productToEdit ? (
        <ProductForm
          title="Editar Produto"
          initialData={{
            ...productToEdit,
            quantity: productToEdit.quantity?.toString() || "0",
            buyPrice: productToEdit.buyPrice?.toString() || "0.00",
            salePrice: productToEdit.salePrice?.toString() || "0.00",
            validDate: formatInstantToInput(productToEdit.validDate),
          }}
          categories={categories}
          suppliers={suppliers}
          loading={editLoading}
          error={editError}
          onSubmit={handleConfirmEdit}
          onCancel={showTable}
        />
      ) : isCreatingProduct ? (
        <ProductForm
          title="Criar Novo Produto"
          initialData={{
            name: "",
            description: "",
            categoryId: "",
            supplierId: "",
            quantity: "0",
            buyPrice: "0.00",
            salePrice: "0.00",
            validDate: "",
          }}
          categories={categories}
          suppliers={suppliers}
          loading={createLoading}
          error={createError}
          onSubmit={handleConfirmCreate}
          onCancel={showTable}
        />
      ) : (
        <ProductTable
          products={products}
          categories={categories}
          suppliers={suppliers}
          loading={loading}
          error={error}
          onEditProduct={handleOpenEditModal}
          onDeleteProduct={handleOpenDeleteModal}
        />
      )}
    </main>
  );
}
