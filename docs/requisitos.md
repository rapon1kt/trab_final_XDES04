## Requisitos

---

#### Requisitos Funcionais de Cliente (RFC)

| Código | Nome | Descrição | Categoria |
|--------|------|------------|------------|
| RFC01 | Autenticação de Usuário | O sistema deve permitir login e logout de usuários, com autenticação por email e senha. | Segurança |
| RFC02 | Personalização de Perfil | O usuário deve poder alterar informações pessoais, senha e foto de perfil. | Usabilidade |
| RFC03 | Níveis de Acesso | O sistema deve ter papéis distintos (Administrador e Operador), com permissões diferenciadas. | Segurança |
| RFC04 | Notificações Visuais | Alertas de baixo estoque, validade vencida e pedidos pendentes devem ser exibidos de forma visual na interface. | Interatividade |

---

#### Requisitos Funcionais de Sistema (RFS)

| Código | Nome | Descrição | Tipo |
|--------|------|------------|------|
| RFS01 | Produto deve ter categoria e fornecedor | Nenhum produto pode ser cadastrado sem categoria e fornecedor válidos. | Restritiva |
| RFS02 | Saída depende de estoque disponível | A movimentação de saída é bloqueada se a quantidade em estoque for insuficiente. | Preventiva |
| RFS03 | Alerta de baixo estoque | Se a quantidade atual < estoque mínimo, o sistema gera alerta e cria pedido de reposição automático. | Automática |
| RFS04 | Bloqueio por validade vencida | Produtos vencidos são marcados como inativos e não podem ser movimentados. | Validatória |
| RFS05 | Movimentações são imutáveis | Após o registro, uma movimentação não pode ser editada, apenas estornada. | Integridade |
| RFS06 | Exclusão restrita | Somente administradores podem excluir produtos, categorias e fornecedores. | Permissão |
| RFS07 | Histórico de alterações | Toda atualização em produto, fornecedor ou movimentação deve ser registrada no histórico (log). | Rastreabilidade |
| RFS08 | Estoque negativo proibido | O sistema não permite que o estoque de um produto fique negativo. | Preventiva |
| RFS09 | Pedido de Reposição Automático | Quando o produto atinge o limite mínimo, um pedido é criado automaticamente e marcado como pendente. | Automação |
| RFS10 | Auditoria de acessos | O sistema deve registrar todas as ações de login, logout e operações críticas realizadas. | Segurança Interna |

---

#### Requisitos Não Funcionais (RNF)

| Código | Nome | Descrição | Categoria |
|--------|------|------------|------------|
| RNF01 | Arquitetura REST | O sistema deve seguir o padrão REST em sua API para padronização e integração. | Arquitetura |
| RNF02 | Tempo de resposta | Cada requisição deve ser processada em menos de 3 segundos. | Desempenho |
| RNF03 | Persistência de dados | Os dados devem ser armazenados de forma segura e persistente em banco PostgreSQL ou MongoDB. | Banco de Dados |
| RNF04 | Segurança de credenciais | As senhas devem ser armazenadas criptografadas (BCrypt, Argon2, etc.) e os tokens devem expirar automaticamente. | Segurança |
| RNF05 | Interface responsiva | O frontend deve se adaptar a diferentes tamanhos de tela e navegadores. | Usabilidade |
| RNF06 | Controle de versão | Todo o projeto deve estar sob versionamento Git, com histórico rastreável de commits. | Manutenibilidade |
