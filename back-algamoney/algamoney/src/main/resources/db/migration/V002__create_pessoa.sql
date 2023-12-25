create table pessoa (
    id bigint(20) primary key auto_increment,
    nome varchar(100) not null,
    rua varchar(50),
    numero varchar(10),
    complemento varchar(100),
    bairro varchar(100),
    cep varchar(15),
    cidade varchar(100),
    estado varchar(20),
    ativo char
) ENGINE=InnoDB default CHARSET=utf8;

INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo)
VALUES
('John Doe', 'Main Street', '123', 'Apt 101', 'Downtown', '12345-678', 'Big City', 'State', 'Y'),
('Jane Smith', 'Park Avenue', '456', NULL, 'Central Park', '98765-432', 'Metropolis', 'State', 'Y'),
('Alice Johnson', 'Broadway', '789', 'Floor 3', 'Theater District', '54321-876', 'City of Dreams', 'State', 'Y');
