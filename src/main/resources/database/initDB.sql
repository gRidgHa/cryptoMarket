CREATE TABLE IF NOT EXISTS USER_TABLE
(
    secret_key VARCHAR(50) PRIMARY KEY,
    user_type  varchar(5)   NOT NULL,
    username   varchar(100) NOT NULL,
    email      varchar(100) NOT NULL,
    BTC_wallet DECIMAL,
    TON_wallet DECIMAL,
    RUB_wallet DECIMAL
);

CREATE TABLE IF NOT EXISTS TRANSACTION_TABLE
(
    operation_date VARCHAR(50) PRIMARY KEY,
    operation_type varchar(50) NOT NULL

);

CREATE TABLE IF NOT EXISTS EXCHANGE_RATE_TABLE
(
    base_currency VARCHAR(3) PRIMARY KEY,
    BTC_wallet DECIMAL not null,
    TON_wallet DECIMAL not null,
    RUB_wallet DECIMAL not null
);

