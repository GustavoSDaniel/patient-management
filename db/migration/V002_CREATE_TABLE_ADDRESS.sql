-- Criação da tabela Address
CREATE TABLE address (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         number VARCHAR(20) NOT NULL,
                         street VARCHAR(255) NOT NULL,
                         zipcode VARCHAR(20) NOT NULL,
                         complement VARCHAR(255) NOT NULL,
                         neighborhood VARCHAR(100) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         state VARCHAR(50) NOT NULL,
                         patient_id UUID,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Constraint de chave estrangeira
                         CONSTRAINT fk_address_patient
                             FOREIGN KEY (patient_id)
                                 REFERENCES patients(id)
                                 ON DELETE CASCADE,

    -- Constraint para garantir relacionamento 1:1
                         CONSTRAINT uk_address_patient
                             UNIQUE (patient_id)
);