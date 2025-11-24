export default interface StockMovement {
  id: string;
  productId: string;
  productName: string;
  type: "IN" | "OUT";
  quantity: number;
  date: string;
  reason: string;
}
