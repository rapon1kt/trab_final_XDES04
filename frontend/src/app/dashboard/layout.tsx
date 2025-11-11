"use client";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { NavBar } from "@/components";
import "./layout.modules.css";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const router = useRouter();
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      router.replace("/");
    } else {
      setIsAuthenticated(true);
    }
  }, [router]);

  if (!isAuthenticated) {
    return <p>Verificando autenticação...</p>;
  }

  return (
    <main className="dashboard-layout">
      <NavBar />
      {children}
    </main>
  );
}
