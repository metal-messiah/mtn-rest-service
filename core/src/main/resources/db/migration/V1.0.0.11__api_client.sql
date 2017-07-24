CREATE SEQUENCE seq_api_client_id;

CREATE TABLE api_client (
  id            INT         NOT NULL,
  name          VARCHAR(64) NOT NULL,
  client_id     VARCHAR(64) NOT NULL,
  client_secret VARCHAR(64) NOT NULL,
  CONSTRAINT pk_api_client PRIMARY KEY (id),
  CONSTRAINT uk_api_client_client_id UNIQUE (client_id)
);

INSERT INTO api_client (id, name, client_id, client_secret) VALUES
  (NEXTVAL('seq_api_client_id'), 'MTN-RS Administration', 'UtMThNghwlK13gzUuRXeF3kUHMQMGMiX',
   '7m0ykt_SX4wb_aYOTHzNZUkVD73U7c8Cye5gWefdu4eih2oYLN-meuPHKnkwd3V8');