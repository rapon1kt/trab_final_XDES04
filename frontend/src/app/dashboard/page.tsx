"use client";
import {
  DashboardSupplier,
  DashboardUser,
  DashboardCategory,
} from "@/components";
import { useSearchParams } from "next/navigation";
import "./page.modules.css";
import DashboardProduct from "@/components/dashboard-product/dashboard-product";

export default function Dashboard() {
  const searchParam = useSearchParams();
  const pageType = searchParam.get("pageType");
  switch (pageType) {
    case "user":
      return (
        <div className="container">
          <DashboardUser />
        </div>
      );
    case "supplier":
      return (
        <div className="container">
          <DashboardSupplier />
        </div>
      );
    case "product":
      return (
        <div className="container">
          <DashboardProduct />
        </div>
      );
    case "category":
      return (
        <div className="container">
          <DashboardCategory />
        </div>
      );
    default:
      return (
        <div className="container">
          <p>{pageType}</p>
        </div>
      );
  }
}
