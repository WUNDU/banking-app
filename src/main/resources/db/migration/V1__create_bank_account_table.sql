CREATE TABLE bank_account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT uk_account_number UNIQUE (account_number),
    CONSTRAINT chk_bank_name CHECK (bank_name <> '')
);

-- Create Card table
CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    last_four_digits VARCHAR(4) NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    expiration_date DATE NOT NULL,
    account_id SERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_card_number UNIQUE (card_number),
    CONSTRAINT fk_card_account FOREIGN KEY (account_id) REFERENCES bank_account(id),
    CONSTRAINT chk_last_four_digits CHECK (last_four_digits ~ '^[0-9]{4}$'),
    CONSTRAINT chk_bank_name CHECK (bank_name <> ''),
    CONSTRAINT chk_expiration_date CHECK (expiration_date > CURRENT_DATE + INTERVAL '6 months')
);

-- Create Transaction table
CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    account_id SERIAL NOT NULL,
    card_id SERIAL,
    amount DOUBLE PRECISION NOT NULL,
    merchant VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES bank_account(id),
    CONSTRAINT fk_transaction_card FOREIGN KEY (card_id) REFERENCES card(id),
    CONSTRAINT chk_amount CHECK (amount > 0),
    CONSTRAINT chk_merchant CHECK (merchant <> ''),
    CONSTRAINT chk_type CHECK (type IN ('COMPRA', 'SAQUE'))
);

-- Create index for foreign keys to improve query performance
CREATE INDEX idx_card_account_id ON card(account_id);
CREATE INDEX idx_transaction_account_id ON transaction(account_id);
CREATE INDEX idx_transaction_card_id ON transaction(card_id);