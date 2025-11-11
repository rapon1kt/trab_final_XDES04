"use client";
import { DashboardSupplier, DashboardUser } from "@/components";
import "./page.modules.css";
import { useSearchParams } from "next/navigation";

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
    default:
      return (
        <div className="container">
          <p>{pageType}</p>
        </div>
      );
  }
}
