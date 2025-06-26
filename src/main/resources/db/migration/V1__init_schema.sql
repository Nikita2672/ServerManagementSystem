CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE departments (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    company_id UUID NOT NULL REFERENCES companies(id) ON DELETE CASCADE
);

CREATE TABLE employees (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    position TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    department_id UUID NOT NULL REFERENCES departments(id) ON DELETE CASCADE
);

CREATE TABLE servers (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    manufacturer TEXT NOT NULL,
    ip TEXT NOT NULL,
    ram_gb INTEGER NOT NULL,
    disk_gb INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    responsible_id UUID NOT NULL REFERENCES employees(id) ON DELETE CASCADE
);
