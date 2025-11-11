import { Address } from "@/domain/model";

export default interface Supplier {
  id: string;
  enterpriseName: string;
  cnpj: string;
  email: string;
  phone: string;
  address: Address;
}
