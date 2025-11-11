"use client";
import { useState, FormEvent } from "react";
import { useRouter } from "next/navigation";
import api from "@/lib/api";
import "./page.modules.css";
import Link from "next/link";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState<any>();
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      const loginData = { email, password };

      const response = await api.post("/req/signin", loginData);

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
            <p className="">Email</p>
            <input
              type="email"
              value={email}
              className="login-input"
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="login-input-div">
            <p>Senha</p>
            <input
              type="password"
              value={password}
              className="login-input"
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button className="login-button" type="submit">
            Entrar
          </button>
          <p className="login-link">
            Registrar nova conta{" "}
            <Link className="login-link-link" href="/register">
              aqui
            </Link>
            !
          </p>
        </form>
      </div>
    </main>
  );
}
