CREATE TABLE IF NOT EXISTS users (
  id    BIGSERIAL PRIMARY KEY,
  name  VARCHAR,
  age   INTEGER NOT NULL
);