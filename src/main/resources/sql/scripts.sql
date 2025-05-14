
-- Activer l'extension pour UUID si non déjà activée
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DROP TABLE IF EXISTS "authorities" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "customer" CASCADE;

CREATE TABLE "customer" (
                            "customer_id" SERIAL PRIMARY KEY,
                            "name" VARCHAR(100) NOT NULL,
                            "email" VARCHAR(100) NOT NULL,
                            "mobile_number" VARCHAR(20) NOT NULL,
                            "pwd" VARCHAR(500) NOT NULL,
                            "role" VARCHAR(100) NOT NULL,
                            "create_dt" DATE DEFAULT NULL
);

INSERT INTO "customer" ("name","email","mobile_number", "pwd", "role","create_dt")
VALUES ('Moustapha','moustapha@example.com','5334122365', '{bcrypt}$2a$12$V.oWpASRJCF5Q7.Kwl15xukifb.zzBS8thZ8Pb6SraU.tiqAxQ4iK', 'admin', CURRENT_DATE);

CREATE TABLE "accounts" (
                            "customer_id" INT NOT NULL,
                            "account_number" INT PRIMARY KEY,
                            "account_type" VARCHAR(100) NOT NULL,
                            "branch_address" VARCHAR(200) NOT NULL,
                            "create_dt" DATE DEFAULT NULL,
                            CONSTRAINT "fk_customer_account" FOREIGN KEY ("customer_id") REFERENCES "customer" ("customer_id") ON DELETE CASCADE
);

INSERT INTO "accounts" ("customer_id", "account_number", "account_type", "branch_address", "create_dt")
VALUES (1, 1865764534, 'Savings', '123 Rue, Grand Yoff', CURRENT_DATE);

INSERT INTO "accounts" (
    customer_id, account_number, account_type, branch_address, create_dt
) VALUES (
             1, 1865764534, 'Savings', '123 Rue, Grand Yoff', CURRENT_DATE
         );


CREATE TABLE "account_transactions" (
                                        "transaction_id" UUID PRIMARY KEY,
                                        "account_number" INT NOT NULL,
                                        "customer_id" INT NOT NULL,
                                        "transaction_dt" DATE NOT NULL,
                                        "transaction_summary" VARCHAR(200) NOT NULL,
                                        "transaction_type" VARCHAR(100) NOT NULL,
                                        "transaction_amt" INT NOT NULL,
                                        "closing_balance" INT NOT NULL,
                                        "create_dt" DATE DEFAULT NULL,
                                        CONSTRAINT "fk_account_transactions_account" FOREIGN KEY ("account_number") REFERENCES "accounts" ("account_number") ON DELETE CASCADE,
                                        CONSTRAINT "fk_account_transactions_customer" FOREIGN KEY ("customer_id") REFERENCES "customer" ("customer_id") ON DELETE CASCADE
);

INSERT INTO "account_transactions" VALUES
    (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '7 days', 'Coffee Shop', 'Withdrawal', 30, 34500, CURRENT_DATE - INTERVAL '7 days')
;


CREATE EXTENSION IF NOT EXISTS pgcrypto;
SELECT * FROM pg_extension WHERE extname = 'pgcrypto';


INSERT INTO "account_transactions" VALUES
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '7 days', 'Coffee Shop', 'Withdrawal', 30,34500, CURRENT_DATE - INTERVAL '7 days'),
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '6 days', 'Yango', 'Withdrawal', 100,34400, CURRENT_DATE - INTERVAL '6 days'),
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '5 days', 'Self Deposit', 'Deposit', 500,34900, CURRENT_DATE - INTERVAL '5 days'),
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '4 days', 'Yassir', 'Withdrawal', 600,34300, CURRENT_DATE - INTERVAL '4 days'),
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '2 days', 'OnlineTransfer', 'Deposit', 700,35000, CURRENT_DATE - INTERVAL '2 days'),
                                       (gen_random_uuid(), 1865764534, 1, CURRENT_DATE - INTERVAL '1 days', 'ExpatDakar', 'Withdrawal', 100,34900, CURRENT_DATE - INTERVAL '1 days');

CREATE TABLE "loans" (
                         "loan_number" SERIAL PRIMARY KEY,
                         "customer_id" INT NOT NULL,
                         "start_dt" DATE NOT NULL,
                         "loan_type" VARCHAR(100) NOT NULL,
                         "total_loan" INT NOT NULL,
                         "amount_paid" INT NOT NULL,
                         "outstanding_amount" INT NOT NULL,
                         "create_dt" DATE DEFAULT NULL,
                         CONSTRAINT "fk_loans_customer" FOREIGN KEY ("customer_id") REFERENCES "customer" ("customer_id") ON DELETE CASCADE
);

INSERT INTO "loans" ("customer_id", "start_dt", "loan_type", "total_loan", "amount_paid", "outstanding_amount", "create_dt") VALUES
                                                                                                                                 (1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13'),
                                                                                                                                 (1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06'),
                                                                                                                                 (1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14'),
                                                                                                                                 (1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

CREATE TABLE "cards" (
                         "card_id" SERIAL PRIMARY KEY,
                         "card_number" VARCHAR(100) NOT NULL,
                         "customer_id" INT NOT NULL,
                         "card_type" VARCHAR(100) NOT NULL,
                         "total_limit" INT NOT NULL,
                         "amount_used" INT NOT NULL,
                         "available_amount" INT NOT NULL,
                         "create_dt" DATE DEFAULT NULL,
                         CONSTRAINT "fk_cards_customer" FOREIGN KEY ("customer_id") REFERENCES "customer" ("customer_id") ON DELETE CASCADE
);

INSERT INTO "cards" ("card_number", "customer_id", "card_type", "total_limit", "amount_used", "available_amount", "create_dt") VALUES
                                                                                                                                   ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURRENT_DATE),
                                                                                                                                   ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURRENT_DATE),
                                                                                                                                   ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURRENT_DATE);

CREATE TABLE "notice_details" (
                                  "notice_id" SERIAL PRIMARY KEY,
                                  "notice_summary" VARCHAR(200) NOT NULL,
                                  "notice_details" VARCHAR(500) NOT NULL,
                                  "notic_beg_dt" DATE NOT NULL,
                                  "notic_end_dt" DATE DEFAULT NULL,
                                  "create_dt" DATE DEFAULT NULL,
                                  "update_dt" DATE DEFAULT NULL
);

INSERT INTO "notice_details" ("notice_summary", "notice_details", "notic_beg_dt", "notic_end_dt", "create_dt", "update_dt") VALUES
                                                                                                                                ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null),
                                                                                                                                ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null),
                                                                                                                                ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null),
                                                                                                                                ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null),
                                                                                                                                ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null),
                                                                                                                                ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE, null);

CREATE TABLE "contact_messages" (
                                    "contact_id" UUID PRIMARY KEY,
                                    "contact_name" VARCHAR(50) NOT NULL,
                                    "contact_email" VARCHAR(100) NOT NULL,
                                    "subject" VARCHAR(500) NOT NULL,
                                    "message" VARCHAR(2000) NOT NULL,
                                    "create_dt" DATE DEFAULT NULL
);

CREATE TABLE authorities (
                             id SERIAL PRIMARY KEY,
                             customer_id INT NOT NULL,
                             name VARCHAR(50) NOT NULL,
                             CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

INSERT INTO authorities (customer_id, name) VALUES
                                                (1, 'VIEWACCOUNT'),
                                                (1, 'VIEWCARDS'),
                                                (1, 'VIEWLOANS'),
                                                (1, 'VIEWBALANCE');

DELETE FROM authorities;

SELECT * FROM authorities;

INSERT INTO authorities (customer_id, name) VALUES
                                                (1, 'ROLE_USER'),
                                                (1, 'ROLE_ADMIN');
