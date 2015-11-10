CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE account (
  id       INT          NOT NULL,
  login    VARCHAR(16)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  role     VARCHAR(16)  NOT NULL,
  CONSTRAINT account_pkey PRIMARY KEY (id)
);

CREATE TABLE restaurant (
  id   INT         NOT NULL,
  name VARCHAR(64) NOT NULL UNIQUE,
  CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);

CREATE TABLE menu (
  id            INT          NOT NULL,
  restaurant_id INT          NOT NULL REFERENCES restaurant (id),
  dish_name     VARCHAR(255) NOT NULL,
  price         DECIMAL      NOT NULL,
  CONSTRAINT menu_pkey PRIMARY KEY (id)
);

CREATE TABLE vote (
  id            INT NOT NULL,
  account_id    INT NOT NULL REFERENCES account (id) UNIQUE,
  restaurant_id INT NOT NULL REFERENCES restaurant (id),
  CONSTRAINT vote_pkey PRIMARY KEY (id)

);