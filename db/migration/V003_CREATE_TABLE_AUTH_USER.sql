CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NULL
);

INSERT INTO users (id, email, password, role, created_at, updated_at)
VALUES (
           gen_random_uuid(),
           'teste@email.com',
           'senha123', -- IMPORTANTE: Veja a nota abaixo sobre a senha
           'USER',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );


ALTER TABLE users ADD COLUMN username VARCHAR(255) NOT NULL DEFAULT 'nome_padrao';