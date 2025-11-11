"use client";
import Link from "next/link";
import "./nav-bar.css";
import { useCallback } from "react";
import { useRouter, usePathname, useSearchParams } from "next/navigation";

export default function NavBar() {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();

  const createQueryString = useCallback(
    (name: string, value: string) => {
      const params = new URLSearchParams(searchParams.toString());
      params.set(name, value);

      return params.toString();
    },
    [searchParams]
  );

  return (
    <header>
      <h1 className="header-title">Painel de Controle</h1>
      <div className="menu">
        <p
          className="menu-item"
          onClick={() =>
            router.push(
              pathname + "?" + createQueryString("pageType", "product")
            )
          }
        >
          Produto
        </p>
        <p
          className="menu-item"
          onClick={() =>
            router.push(
              pathname + "?" + createQueryString("pageType", "supplier")
            )
          }
        >
          Fornecedor
        </p>
        <p
          className="menu-item"
          onClick={() =>
            router.push(
              pathname + "?" + createQueryString("pageType", "movement")
            )
          }
        >
          Movimentação
        </p>
        <p
          className="menu-item"
          onClick={() =>
            router.push(pathname + "?" + createQueryString("pageType", "user"))
          }
        >
          Usuário
        </p>
        <p
          className="menu-item"
          onClick={() => {
            localStorage.removeItem("authToken");
            router.push("/");
          }}
        >
          Sair
        </p>
      </div>
    </header>
  );
}
