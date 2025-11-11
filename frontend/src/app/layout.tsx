import type { Metadata } from "next";
import { Roboto_Mono } from "next/font/google";
import "./globals.css";

export const metadata: Metadata = {
  title: "Gestor De Estoque",
  description: "Created by rapon1kt",
};

const roboto = Roboto_Mono({
  subsets: ["latin"],
  weight: ["400", "600", "700"],
});

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html className={roboto.className} lang="pt-br">
      <body>{children}</body>
    </html>
  );
}
