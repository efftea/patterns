CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         lastName VARCHAR(100) NOT NULL,
                         firstName VARCHAR(100) NOT NULL,
                         middleName VARCHAR(100),
                         phone VARCHAR(20),
                         telegram VARCHAR(50),
                         email VARCHAR(100),
                         github VARCHAR(100)
)