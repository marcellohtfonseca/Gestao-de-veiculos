-- ============================================================
-- Modelo Físico - Sistema de Gestão Automotiva
-- Banco de Dados: MySQL 8.x
-- ============================================================

CREATE DATABASE IF NOT EXISTS gestao_automotiva
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestao_automotiva;

-- ------------------------------------------------------------
-- Tabela: marca
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS marca (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    nome        VARCHAR(100)    NOT NULL,
    pais_origem VARCHAR(100)    NOT NULL,
    CONSTRAINT pk_marca PRIMARY KEY (id),
    CONSTRAINT uq_marca_nome UNIQUE (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Tabela: modelo
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS modelo (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    categoria VARCHAR(50)  NOT NULL,
    marca_id  BIGINT       NOT NULL,
    CONSTRAINT pk_modelo PRIMARY KEY (id),
    CONSTRAINT uq_modelo_nome_marca UNIQUE (nome, marca_id),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id)
        REFERENCES marca (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Tabela: veiculo
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS veiculo (
    id             BIGINT          NOT NULL AUTO_INCREMENT,
    cor            VARCHAR(50)     NOT NULL,
    ano            INT             NOT NULL,
    preco          DECIMAL(12, 2)  NOT NULL,
    quilometragem  INT             NOT NULL DEFAULT 0,
    status         VARCHAR(20)     NOT NULL,
    placa          VARCHAR(10)     NOT NULL,
    chassi         VARCHAR(17)     NOT NULL,
    modelo_id      BIGINT          NOT NULL,
    CONSTRAINT pk_veiculo PRIMARY KEY (id),
    CONSTRAINT uq_veiculo_placa  UNIQUE (placa),
    CONSTRAINT uq_veiculo_chassi UNIQUE (chassi),
    CONSTRAINT chk_veiculo_ano   CHECK (ano BETWEEN 1900 AND 2100),
    CONSTRAINT chk_veiculo_preco CHECK (preco > 0),
    CONSTRAINT chk_veiculo_km    CHECK (quilometragem >= 0),
    CONSTRAINT chk_veiculo_status CHECK (
        status IN ('DISPONIVEL','VENDIDO','RESERVADO','MANUTENCAO','DESCONTINUADO')
    ),
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id)
        REFERENCES modelo (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Índices para consultas frequentes
-- ------------------------------------------------------------
CREATE INDEX idx_veiculo_status    ON veiculo (status);
CREATE INDEX idx_veiculo_ano       ON veiculo (ano);
CREATE INDEX idx_veiculo_preco     ON veiculo (preco);
CREATE INDEX idx_veiculo_modelo_id ON veiculo (modelo_id);
CREATE INDEX idx_modelo_marca_id   ON modelo (marca_id);

-- ------------------------------------------------------------
-- Dados de exemplo
-- ------------------------------------------------------------

-- Marcas
INSERT INTO marca (nome, pais_origem) VALUES
    ('Toyota',      'Japão'),
    ('Volkswagen',  'Alemanha'),
    ('Fiat',        'Itália'),
    ('Chevrolet',   'Estados Unidos'),
    ('Honda',       'Japão');

-- Modelos
INSERT INTO modelo (nome, categoria, marca_id) VALUES
    ('Corolla',   'Sedan',    1),
    ('Hilux',     'Picape',   1),
    ('Gol',       'Hatch',    2),
    ('Polo',      'Hatch',    2),
    ('Strada',    'Picape',   3),
    ('Pulse',     'SUV',      3),
    ('Onix',      'Hatch',    4),
    ('S10',       'Picape',   4),
    ('Civic',     'Sedan',    5),
    ('HR-V',      'SUV',      5);

-- Veículos
INSERT INTO veiculo (cor, ano, preco, quilometragem, status, placa, chassi, modelo_id) VALUES
    ('Branco',   2022, 145000.00,  15000, 'DISPONIVEL',    'ABC1D23', '9BWZZZ377VT004251', 1),
    ('Prata',    2021, 210000.00,  32000, 'DISPONIVEL',    'DEF2E34', '9BWZZZ377VT004252', 2),
    ('Vermelho', 2020,  55000.00,  60000, 'DISPONIVEL',    'GHI3F45', '9BWZZZ377VT004253', 3),
    ('Preto',    2023, 105000.00,   5000, 'RESERVADO',     'JKL4G56', '9BWZZZ377VT004254', 4),
    ('Azul',     2022,  98000.00,  22000, 'DISPONIVEL',    'MNO5H67', '9BWZZZ377VT004255', 5),
    ('Cinza',    2023, 130000.00,   8000, 'DISPONIVEL',    'PQR6I78', '9BWZZZ377VT004256', 6),
    ('Branco',   2021,  72000.00,  45000, 'VENDIDO',       'STU7J89', '9BWZZZ377VT004257', 7),
    ('Prata',    2022, 195000.00,  18000, 'DISPONIVEL',    'VWX8K90', '9BWZZZ377VT004258', 8),
    ('Preto',    2023, 165000.00,   3000, 'DISPONIVEL',    'YZA9L01', '9BWZZZ377VT004259', 9),
    ('Vermelho', 2022, 140000.00,  25000, 'MANUTENCAO',    'BCD0M12', '9BWZZZ377VT004260', 10);
