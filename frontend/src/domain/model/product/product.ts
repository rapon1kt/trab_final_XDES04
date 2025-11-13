export default interface Product {
  id: string;
  name: string;
  description: string;
  categoryId: string;
  supplierId: string;
  quantity: number;
  buyPrice: number;
  salePrice: number;
  validDate: string;
}
