#### Produto

| Atributo | Tipo de Dado |
|-----------|--------------|
| id | ObjectId (String) |
| nome | String |
| status | ENUM('ATIVO', 'INATIVO', 'VENCIDO') |
| descricao | String |
| categoriaId | ObjectId (String) |
| fornecedorId | ObjectID (String) | 
| quantidadeAtual | Int |
| estoqueMinimo | Int |
| precoCompra | Float |
| precoVenda | Float |
| dataValidade | DATE |
| criadoEm | Instant | Default: NOW() |
| atualizadoEm | Instant |

---

#### Categoria

| Atributo | Tipo de Dado | 
|-----------|--------------|
| id | ObjectId (String) | 
| nome | String | 
| descricao | String | 
| criadoEm | Instant | 

---

####  Fornecedor

| Atributo | Tipo de Dado |
|-----------|--------------|
| id | ObjectId (String) |
| ativo | Boolean | 
| nome | String |
| cnpj | String |
| telefone | String | 
| email | String | 
| endereco | Endereco | 
| criadoEm | Instant | 

---

#### Movimentacao

| Atributo | Tipo de Dado | 
|-----------|--------------|
| id | ObjectId (String) | 
| produtoId | ObjectId (String) |
| usuarioId | ObjectId (String) |
| tipo | ENUM('ENTRADA', 'SAIDA') |
| quantidade | Int |
| data_movimentacao | Instant | 
| motivo | String |
| observacoes | String | 

---

#### Usuario

| Atributo | Tipo de Dado | 
|-------------------------|------------------------|
| id | ObjectId (String) | 
| nome | String | 
| email | String | 
| senha | String |
| foto | String | 
| ativo | Boolean | 
| criadoEm | Instant |
| cargo | ENUM('ADMIN', 'OPERADOR') |

---

#### PedidoReposicao

| Atributo | Tipo de Dado | 
|-----------|--------------|
| id | ObjectId (String) | 
| produtoId | ObjectId (String) | 
| fornecedorId | ObjectId (String) | 
| usuarioId | ObjectId (String) | 
| quantidade | Int | 
| dataPedido | Instant | 
| status | ENUM('PENDENTE', 'APROVADO', 'ENTREGUE', 'CANCELADO') |
| observacoes | String | 
