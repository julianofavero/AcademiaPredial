# AcademiaPredial
SA6

#comandos Mysql

create database `AcademiaPredial`;
USE `AcademiaPredial`;
create table `cliente` (
	codigo INT(3) NOT NULL AUTO_INCREMENT,
	nome VARCHAR(120) NOT NULL,
	apartamento int(220) NOT NULL,
	dataReserva VARCHAR(120),
    horario varchar(120),
    matricula int(3),
	PRIMARY KEY (codigo)
)
