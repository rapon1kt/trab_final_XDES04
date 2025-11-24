export default interface ReplenishmentRequest {
  id: string;
  productId: string;
  productName: string;
  supplierName: string;
  quantity: number;
  status: "OPEN" | "COMPLETED" | "CANCELED";
  createdAt: string;
}
