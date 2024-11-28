CREATE DATABASE comerciodb;

USE comerciodb;

CREATE TABLE produtos ( 
    produtoId INT UNIQUE NOT NULL PRIMARY KEY,
    nome VARCHAR(30) NOT NULL,
    descricao VARCHAR(100),
    preco VARCHAR(10) NOT NULL,
    qntEstoque VARCHAR(5) NOT NULL,
    categoria VARCHAR(30) NOT NULL
);

CREATE TABLE pedidos ( 
    pedidoId INT UNIQUE NOT NULL PRIMARY KEY,
    produtos VARCHAR(100) NOT NULL,
    dataPedido VARCHAR(10) NOT NULL,
    valorTotal VARCHAR(10) NOT NULL,
    formaPag VARCHAR(30) NOT NULL,
    statusPed VARCHAR(30) NOT NULL
);

INSERT INTO produtos (produtoId, nome, descricao, preco, qntEstoque, categoria) VALUES
    ('1', 'SAMSUNG S20FE', 'Smartphone Samsung Galaxy S20FE 8/128gb processador octa core', '1249.99', '50', 'Eletrônicos'),
    ('2', 'Poco X6 Pro', 'Smartphone PocoFone X6 Pro 12/512gb processador dimensity 8300', '1799.99', '15', 'Eletrônicos');

INSERT INTO pedidos (pedidoId, produtos, dataPedido, valorTotal, formaPag, statusPed) VALUES 
    ('2', 'Poco X6 Pro', '23/11/2024', '1799.99', 'PIX', 'Em Processamento'),
    ('1', 'SAMSUNG S20FE', '27/05/2022', '1399.99', 'Cartão', 'Concluído');