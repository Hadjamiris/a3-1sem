CREATE DATABASE controle_estoque;

-----------------
USE controle_estoque;
---------------
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tamanho VARCHAR(20),
    embalagem VARCHAR(20)
);
-------------------
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_unitario DOUBLE NOT NULL,
    unidade VARCHAR(20),
    quantidade INT NOT NULL,
    quantidade_minima INT,
    quantidade_maxima INT,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);
