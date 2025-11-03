
CREATE TYPE TIPOGOLPE AS ENUM ('Golpe do Presente', 'Phishing / Roubo de credenciais', 'Taxa de entrega / Frete falso', 'Transferência PIX', 'Outro');
CREATE TYPE TIPOCANAL AS ENUM ('Whatsapp', 'SMS', 'Instagram', 'Web');
CREATE TYPE TIPODADO AS ENUM ('CPF', 'Senha', 'Dados do cartão', 'Transferência do PIX', 'E-mail', 'Outros');

CREATE TABLE TELEFONE_TABLE(
  id uuid primary key,
  ddd char(3) not null,
  numero char(9) not null,
  constraint NUMERO_DDD UNIQUE (ddd, numero)
);

CREATE TABLE DADO_ENVOLVIDO_TABLE(
  id uuid primary key,
  tipo_dado TIPODADO not null,
  informacao varchar not null
);

CREATE TABLE EMPRESA_TABLE (
  id uuid PRIMARY KEY,
  razao varchar NOT NULL,
  nome_fantasia varchar NOT NULL,
  cnpj char(14) not null unique,
  email_corporativo varchar not null,
  site_oficial varchar not null,
  senha varchar not null,
  telefone_id uuid not null,
  constraint fk_telefone_empresa foreign key (telefone_id) references TELEFONE_TABLE(id)
);

CREATE TABLE LINKS_TABLE (
  id uuid primary key,
  link_real varchar not null,
  link_encurtado varchar not null,
  empresa_id uuid not null,
  constraint fk_empresa_id foreign key (empresa_id) references EMPRESA_TABLE(id)
);

CREATE TABLE CLIENTE_TABLE(
  id uuid primary key,
  nome varchar not null,
  cpf char(11) unique not null,
  email varchar not null,
  senha varchar not null,
  telefone_id uuid not null,
  constraint fk_telefone_cliente foreign key (telefone_id) references TELEFONE_TABLE(id)
    
);

CREATE TABLE RELATO_TABLE(
  id uuid primary key,
  tipo_golpe TIPOGOLPE not null,
  canal TIPOCANAL not null,
  descricao varchar,
  data date not null,
  dado_id uuid not null,
  cliente_id uuid not null,
  constraint fk_dado_envolvido foreign key (dado_id) references DADO_ENVOLVIDO_TABLE(id),
  constraint fk_cliente_relato foreign key (cliente_id) references CLIENTE_TABLE(id)
);
