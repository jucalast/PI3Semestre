-- Criação da tabela TB_USER
CREATE TABLE TB_USER (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- Identificador único, gerado automaticamente
    USER_NAME NVARCHAR(255) NOT NULL,    -- Nome completo do usuário
    EMAIL_ID NVARCHAR(255) NOT NULL UNIQUE, -- Endereço de e-mail, único
    CPF NVARCHAR(14) NULL UNIQUE,         -- CPF, único
    MOBILE_NUMBER NVARCHAR(15) NULL,      -- Número de telefone (opcional)
    PASSWORD NVARCHAR(255) NULL,          -- Senha do usuário
    ROLES NVARCHAR(255) NOT NULL           -- Funções atribuídas ao usuário
);

-- Criação da tabela TB_USER_ADDRESS para a relação N:N
CREATE TABLE TB_USER_ADDRESS (
    USER_ID BIGINT NOT NULL,               -- Chave estrangeira referenciando TB_USER
    ADDRESS_ID BIGINT NOT NULL,            -- Chave estrangeira referenciando AddressModel
    PRIMARY KEY (USER_ID, ADDRESS_ID),     -- Chave primária composta
    FOREIGN KEY (USER_ID) REFERENCES TB_USER(id) ON DELETE CASCADE, -- Relacionamento com TB_USER
    FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS_MODEL(id) ON DELETE CASCADE -- Ajuste para a tabela correspondente de endereços
);

INSERT INTO TB_USER (USER_NAME, EMAIL_ID, CPF, MOBILE_NUMBER, PASSWORD, ROLES)
VALUES 
('Giovanni Silva', 'giovanni@example.com', '123.456.789-00', '11987654321', 'senhaSecreta1', 'USER'),
('Maria Oliveira', 'maria@example.com', '987.654.321-00', '11987654322', 'senhaSecreta2', 'USER'),
('Carlos Pereira', 'carlos@example.com', '321.654.987-00', '11987654323', 'senhaSecreta3', 'ADMIN'),
('Ana Souza', 'ana@example.com', '456.123.789-00', '11987654324', 'senhaSecreta4', 'USER'),
('Felipe Santos', 'felipe@example.com', '654.321.987-00', '11987654325', 'senhaSecreta5', 'USER'),
('Juliana Lima', 'juliana@example.com', '789.123.456-00', '11987654326', 'senhaSecreta6', 'ADMIN');
