"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import api from "@/lib/api";
import "./page.modules.css";

interface FormElements extends HTMLFormControlsCollection {
  email: HTMLInputElement;
  password: HTMLInputElement;
}

interface LoginFormElement extends HTMLFormElement {
  readonly elements: FormElements;
}

export default function Login() {
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    if (typeof window !== "undefined") {
      if (localStorage.getItem("authToken")) {
        localStorage.removeItem("authToken");
      }
    }
  }, []);

  const handleSubmit = async (e: React.FormEvent<LoginFormElement>) => {
    e.preventDefault();
    setError(null);
    try {
      const formElements = e.currentTarget.elements;

      const response = await api.post("/req/signin", {
        email: formElements.email.value,
        password: formElements.password.value,
      });

      const token: string = response.data;

      if (!token) {
        throw new Error("Token não recebido do servidor");
      }

      localStorage.setItem("authToken", token);

      router.push("/dashboard");
    } catch (err: any) {
      console.error(err);
      setError(
        err.response?.data?.message || "Falha no login! Verifique seus dados."
      );
    }
  };

  return (
    <main>
      {error && (
        <div className="message-box">
          <p className="p-error">{error}</p>
        </div>
      )}
      <div className="login-container">
        <h2 className="login-title">Login</h2>
        <form className="login-forms" onSubmit={handleSubmit}>
          <div className="login-input-div">
            <p>Email</p>
            <input name="email" type="email" className="login-input" required />
          </div>
          <div className="login-input-div">
            <p>Senha</p>
            <input
              name="password"
              type="password"
              className="login-input"
              required
            />
          </div>
          <button className="login-button" type="submit">
            Entrar
          </button>
        </form>
      </div>
    </main>
  );
}
