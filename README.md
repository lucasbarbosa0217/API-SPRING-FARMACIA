Peço desculpas por isso! Vou corrigir e enviar o `README.md` inteiro no formato de código.

```markdown
# Farmácia

Este é um projeto de uma aplicação de gerenciamento de produtos e categorias em uma farmácia, desenvolvida com Spring Boot e com uma API REST para interagir com o sistema. A aplicação permite criar, atualizar, listar e excluir produtos e categorias.

## Índice

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação e Configuração](#instalação-e-configuração)
- [Uso](#uso)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Descrição

Este projeto é uma aplicação para gerenciamento de produtos e categorias em uma farmácia. As principais funcionalidades incluem:

- **Gerenciar Categorias**: Criação, listagem e exclusão de categorias.
- **Gerenciar Produtos**: Criação, listagem, atualização e exclusão de produtos, associados a categorias.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada.
- **Spring Boot**: Framework para desenvolvimento da aplicação.
- **Spring Data JPA**: Para interação com o banco de dados.
- **H2 Database**: Banco de dados em memória utilizado para desenvolvimento e testes.
- **JUnit**: Framework para testes unitários.
- **TestRestTemplate**: Para testes de integração da API REST.

## Instalação e Configuração

### Pré-requisitos

Certifique-se de ter o JDK 17 ou superior instalado em sua máquina.

### Clonar o Repositório

```bash
git clone https://github.com/lucasbarbosa0217/farmacia.git
```

### Navegar para o Diretório do Projeto

```bash
cd farmacia
```

### Construir e Executar a Aplicação

Para construir e executar a aplicação, use o Maven:

```bash
./mvnw spring-boot:run
```

### Executar Testes

Para executar os testes unitários e de integração, use o Maven:

```bash
./mvnw test
```

## Uso

### Endpoints Disponíveis

#### Categorias

- **Criar Categoria**: `POST /categoria`
- **Listar Categorias**: `GET /categoria`
- **Atualizar Categoria**: `PUT /categoria`
- **Excluir Categoria**: `DELETE /categoria/{id}`

#### Produtos

- **Criar Produto**: `POST /produto`
- **Listar Produtos**: `GET /produto`
- **Listar Produto por ID**: `GET /produto/{id}`
- **Listar Produto por Nome**: `GET /produto/nome/{nome}`
- **Atualizar Produto**: `PUT /produto`
- **Excluir Produto**: `DELETE /produto/{id}`

### Exemplos de Requisições

#### Criar Produto

```http
POST /produto
Content-Type: application/json

{
  "nome": "Dipirona 20mg",
  "estoque": 10,
  "preco": 10.99,
  "categoria": {
    "id": 1
  }
}
```

#### Listar Produtos

```http
GET /produto
```

## Testes

Os testes são configurados para garantir que a aplicação funcione corretamente. Eles cobrem a criação, atualização, listagem e exclusão de produtos e categorias.

Para mais informações sobre como executar os testes, consulte a seção [Executar Testes](#executar-testes).

## Contribuição

Sinta-se à vontade para contribuir com o projeto. Para contribuir, siga estas etapas:

1. Faça um fork deste repositório.
2. Crie uma branch com sua nova funcionalidade (`git checkout -b minha-nova-funcionalidade`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova funcionalidade'`).
4. Faça push para a branch (`git push origin minha-nova-funcionalidade`).
5. Crie um Pull Request.

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.
```

Copie e cole esse código em um arquivo chamado `README.md` no diretório raiz do seu projeto. Se precisar de mais ajuda, é só avisar!
