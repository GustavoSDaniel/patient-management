-- Primeiro, remove a coluna address (agora é relacionamento separado)
ALTER TABLE patients
    DROP COLUMN IF EXISTS address;

-- Atualiza a estrutura da tabela patients para relacionamento 1:1
ALTER TABLE patients
    ALTER COLUMN registration_date TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP;

-- Remove constraints antigos se existirem
ALTER TABLE patients
    DROP CONSTRAINT IF EXISTS chk_birth_date,
    DROP CONSTRAINT IF EXISTS chk_email_format;

-- Cria a tabela address com relacionamento correto
CREATE TABLE IF NOT EXISTS address (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                       number VARCHAR(20) NOT NULL,
                                       street VARCHAR(255) NOT NULL,
                                       zipcode VARCHAR(20) NOT NULL,
                                       complement VARCHAR(255) NOT NULL,
                                       neighborhood VARCHAR(100) NOT NULL,
                                       city VARCHAR(100) NOT NULL,
                                       state VARCHAR(50) NOT NULL,
                                       patient_id UUID UNIQUE, -- UNIQUE garante relacionamento 1:1
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key para patients
                                       CONSTRAINT fk_address_patient
                                           FOREIGN KEY (patient_id)
                                               REFERENCES patients(id)
                                               ON DELETE CASCADE
);

-- Trigger para atualizar updated_at automaticamente na tabela patients
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Aplica o trigger na tabela patients
DROP TRIGGER IF EXISTS update_patients_updated_at ON patients;
CREATE TRIGGER update_patients_updated_at
    BEFORE UPDATE ON patients
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Aplica o trigger na tabela address
DROP TRIGGER IF EXISTS update_address_updated_at ON address;
CREATE TRIGGER update_address_updated_at
    BEFORE UPDATE ON address
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
