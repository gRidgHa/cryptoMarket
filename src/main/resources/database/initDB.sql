CREATE TABLE IF NOT EXISTS USER_TABLE
(
    secret_key VARCHAR(50) PRIMARY KEY,
    user_type  varchar(5)   NOT NULL,
    username   varchar(100) NOT NULL,
    email      varchar(100) NOT NULL,
    BTC_wallet DECIMAL,
    TON_wallet DECIMAL,
    RUB_wallet DECIMAL
)
