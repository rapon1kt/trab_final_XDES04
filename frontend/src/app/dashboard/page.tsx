"use client";
import { useSearchParams } from "next/navigation";
import {
  DashboardUser,
  DashboardSupplier,
  DashboardCategory,
  DashboardProduct,
  DashboardMovement,
  DashboardReplenishment,
  DashboardReport,
} from "@/components";
import "./page.modules.css";

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
    case "category":
      return (
        <div className="container">
          <DashboardCategory />
        </div>
      );
    case "product":
      return (
        <div className="container">
          <DashboardProduct />
        </div>
      );

    // --- NOVOS CASOS ---
    case "movement":
      return (
        <div className="container">
          <DashboardMovement />
        </div>
      );
    case "replenishment":
      return (
        <div className="container">
          <DashboardReplenishment />
        </div>
      );
    case "report":
      return (
        <div className="container">
          <DashboardReport />
        </div>
      );

    default:
      return (
        <div className="container">
          <p>Selecione uma opção no menu.</p>
        </div>
      );
  }
}
