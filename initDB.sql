CREATE TABLE company (
company_id SERIAL PRIMARY KEY,
company_name VARCHAR(100) NOT NULL,
rating VARCHAR(50),
CHECK (rating IN ('low', 'middle', 'high'))
);

CREATE TABLE developer (
developer_id SERIAL PRIMARY KEY,
lastName VARCHAR(50) NOT NULL,
firstName VARCHAR(50),
age SMALLINT NOT NULL,
salary SMALLINT,
company_id BIGINT NOT NULL,
FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE skill (
skill_id SERIAL PRIMARY KEY,
language VARCHAR(50),
level VARCHAR(50) CHECK (level IN ('junior', 'middle', 'senior'))
);

CREATE TABLE developer_skill (
developer_id BIGINT NOT NULL,
skill_id BIGINT NOT NULL,
PRIMARY KEY (developer_id, skill_id),
FOREIGN KEY(developer_id) REFERENCES developer (developer_id),
FOREIGN KEY (skill_id) REFERENCES skill (skill_id)
);

CREATE TABLE customer (
customer_id SERIAL PRIMARY KEY,
customer_name VARCHAR(100) NOT NULL,
reputation VARCHAR(50) CHECK (reputation IN ('insolvent', 'trustworthy', 'respectable'))
);

CREATE TABLE project (
project_id SERIAL PRIMARY KEY,
project_name VARCHAR(100) NOT NULL,
company_id BIGINT NOT NULL,
customer_id BIGINT NOT NULL,
cost INTEGER,
start_date DATE NOT NULL,
FOREIGN KEY (company_id) REFERENCES company (company_id),
FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE project_developer (
project_id BIGINT NOT NULL,
developer_id BIGINT NOT NULL,
PRIMARY KEY (project_id, developer_id),
FOREIGN KEY (project_id ) REFERENCES project (project_id),
FOREIGN KEY (developer_id) REFERENCES developer (developer_id)
);
