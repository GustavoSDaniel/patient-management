CREATE TABLE patients (

                          id UUID PRIMARY KEY,
                          name VARCHAR(255),
                          email VARCHAR(255) UNIQUE,
                          address VARCHAR(255),
                          birth_date DATE,
                          registration_date DATE
);