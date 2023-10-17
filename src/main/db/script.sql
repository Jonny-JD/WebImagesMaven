CREATE DATABASE  web_storage;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR (128) NOT NULL,
                                     email VARCHAR (128) UNIQUE NOT NULL,
                                     password varchar (256) NOT NULL
);

CREATE TABLE IF NOT EXISTS salt (
                                    id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                                    salt VARCHAR (256) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_images (
                                           id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                                           uri VARCHAR(128) NOT NULL
)
