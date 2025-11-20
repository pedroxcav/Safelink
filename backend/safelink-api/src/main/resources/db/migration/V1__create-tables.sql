CREATE TABLE TELEFONE_TABLE(
  id uuid PRIMARY KEY,
  ddd char(3) NOT NULL,
  numero char(9) NOT NULL,
  CONSTRAINT NUMERO_DDD UNIQUE (ddd, numero)
);

CREATE TABLE USUARIO_TABLE (
  id uuid PRIMARY KEY,
  nome varchar NOT NULL,
  email varchar NOT NULL UNIQUE,
  senha varchar NOT NULL,
  telefone_id uuid NOT NULL,
  role smallint NOT NULL,

  CONSTRAINT fk_telefone_usuario
    FOREIGN KEY (telefone_id) REFERENCES TELEFONE_TABLE(id)
);

CREATE TABLE EMPRESA_TABLE (
  usuario_id uuid PRIMARY KEY,
  razao varchar NOT NULL UNIQUE,
  cnpj char(18) NOT NULL UNIQUE,
  site varchar NOT NULL UNIQUE,

  CONSTRAINT fk_empresa_usuario
    FOREIGN KEY (usuario_id) REFERENCES USUARIO_TABLE(id)
      ON DELETE CASCADE
);

CREATE TABLE CLIENTE_TABLE (
  usuario_id uuid PRIMARY KEY,
  cpf char(14) NOT NULL UNIQUE,

  CONSTRAINT fk_cliente_usuario
    FOREIGN KEY (usuario_id) REFERENCES USUARIO_TABLE(id)
      ON DELETE CASCADE
);

CREATE TABLE LINK_TABLE (
  id uuid PRIMARY KEY,
  link_real varchar NOT NULL,
  link_encurtado varchar NOT NULL,
  empresa_id uuid NOT NULL,

  CONSTRAINT fk_link_empresa
    FOREIGN KEY (empresa_id) REFERENCES EMPRESA_TABLE(usuario_id)
      ON DELETE CASCADE
);

CREATE TABLE RELATO_TABLE(
  id uuid PRIMARY KEY,
  tipo_golpe VARCHAR NOT NULL,
  canal VARCHAR NOT NULL,
  descricao varchar,
  data date NOT NULL,
  tipo_dado VARCHAR NOT NULL,
  informacao varchar NOT NULL,
  cliente_id uuid NOT NULL,

  CONSTRAINT fk_relato_cliente
    FOREIGN KEY (cliente_id) REFERENCES CLIENTE_TABLE(usuario_id)
);