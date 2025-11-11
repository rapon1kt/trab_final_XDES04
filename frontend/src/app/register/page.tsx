"use client";

import React, { useState, FormEvent } from "react";
import { useRouter } from "next/navigation";
import api from "@/lib/api";
import Link from "next/link";
import "./page.modules.css";

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    if (password !== confirmPassword) {
      setError("As senhas não conferem.");
      return;
    }

    try {
      await api.post("/req/signup", { name, email, password });

      setSuccess("Conta criada com sucesso! Redirecionando para o login...");

      setTimeout(() => {
        router.push("/");
      }, 2000);
    } catch (err: any) {
      setError(err.response?.data?.message || "Erro ao criar conta.");
    }
  };

  return (
    <main>
      {(error || success) && (
        <div className="message-box">
          {error && <p className="p-error">{error}</p>}
          {success && <p className="p-success">{success}</p>}
        </div>
      )}
      <div className="register-container">
        <h2 className="register-title">Registro</h2>
        <form className="register-forms" onSubmit={handleSubmit}>
          <div className="register-input-div">
            <p className="input-label">Nome</p>
            <input
              type="text"
              value={name}
              className="register-input"
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="register-input-div">
            <p className="input-label">Email</p>
            <input
              type="email"
              value={email}
              className="register-input"
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="register-input-div">
            <p className="input-label">Senha</p>
            <input
              type="password"
              value={password}
              className="register-input"
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="register-input-div">
            <p className="input-label">Confirmar Senha</p>
            <input
              type="password"
              value={confirmPassword}
              className="register-input"
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>
          <button className="register-button" type="submit">
            Registrar
          </button>
        </form>
        <p className="register-link">
          Já tem uma conta?{" "}
          <Link className="register-link-link" href="/">
            Faça login
          </Link>
        </p>
      </div>
    </main>
  );
}
