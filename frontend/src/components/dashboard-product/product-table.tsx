"use client";
import { Product, Category, Supplier } from "@/domain/model";

interface ProductTableProps {
  products: Product[];
  categories: Category[];
  suppliers: Supplier[];
  loading: boolean;
  error: string | null;
  onEditProduct: (product: Product) => void;
  onDeleteProduct: (product: Product) => void;
}

export default function ProductTable({
  products,
  categories,
  suppliers,
  loading,
  error,
  onEditProduct,
  onDeleteProduct,
}: ProductTableProps) {
  const getCategoryName = (id: string) => {
    return categories.find((c) => c.id === id)?.name || "N/A";
  };
  const getSupplierName = (id: string) => {
    return suppliers.find((s) => s.id === id)?.enterpriseName || "N/A";
  };

  if (loading) return <p>Carregando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <table className="dashboard-table">
      <thead>
        <tr className="dashboard-table-header">
          <th className="dashboard-table-title">Nome</th>
          <th className="dashboard-table-title">Categoria</th>
          <th className="dashboard-table-title">Fornecedor</th>
          <th className="dashboard-table-title">Qtd.</th>
          <th className="dashboard-table-title">Preço Venda</th>
          <th className="dashboard-table-title">Validade</th>
          <th className="dashboard-table-title">Ações</th>
        </tr>
      </thead>
      <tbody>
        {products.map((product) => (
          <tr className="dashboard-table-body" key={product.id}>
            <td className="dashboard-table-value">{product.name}</td>
            <td className="dashboard-table-value">
              {getCategoryName(product.categoryId)}
            </td>
            <td className="dashboard-table-value">
              {getSupplierName(product.supplierId)}
            </td>
            <td className="dashboard-table-value">{product.quantity}</td>
            <td className="dashboard-table-value">
              {product.salePrice?.toLocaleString("pt-BR", {
                style: "currency",
                currency: "BRL",
              })}
            </td>
            <td className="dashboard-table-value">
              {new Date(Date.now()).toLocaleDateString()}
            </td>
            <td className="dashboard-table-value actions">
              <button
                className="dashboard-table-button edit"
                onClick={() => onEditProduct(product)}
              >
                Editar
              </button>
              <button
                className="dashboard-table-button cancel"
                onClick={() => onDeleteProduct(product)}
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
