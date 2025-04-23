create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);


-- CREATE TABLE users (username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(500) NOT NULL, enabled BOOLEAN NOT NULL);
-- CREATE TABLE authorities (username VARCHAR(50) NOT NULL, authority VARCHAR(50) NOT NULL, CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username));
-- CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);


INSERT  INTO users VALUES ('user', '{noop}65z3,G4Uz[b.Un', '1') ON CONFLICT (username) DO NOTHING;
INSERT  INTO authorities VALUES ('user', 'read') ON CONFLICT (username, authority) DO NOTHING;

INSERT  INTO users VALUES ('admin', '{bcrypt}$2a$12$iONr95CtVRPP13nhjJ0RHOmekhQKoZRuHN8q9WvszJnhMNv1zWd3e', '1') ON CONFLICT (username) DO NOTHING;
INSERT  INTO authorities VALUES ('admin', 'admin') ON CONFLICT (username, authority) DO NOTHING;

--SELECT * FROM users WHERE username = 'user';

--SELECT * FROM users WHERE username = 'admin';

--UPDATE users SET password = '{noop}65z3,G4Uz[b.Un' WHERE username = 'user';

--UPDATE users SET password = '{bcrypt}$2a$12$VtshWPorLASnVio8TICBPO4TroEzxyBHmMyKCJ1fZWWdxBiEQQAbW' WHERE username = 'admin'; --G[6n,5bU3zU.4z

-- SELECT * FROM authorities WHERE username = 'user';


CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          email VARCHAR(45) NOT NULL,
                          pwd VARCHAR(200) NOT NULL,
                          role VARCHAR(45) NOT NULL
);

INSERT INTO customer (email, pwd, role) VALUES
                                            ('user@demo.com', '{noop}65z3,G4Uz[b.Un', 'read'),
                                            ('admin@demo.com', '{bcrypt}$2a$12$iONr95CtVRPP13nhjJ0RHOmekhQKoZRuHN8q9WvszJnhMNv1zWd3e', 'admin');
