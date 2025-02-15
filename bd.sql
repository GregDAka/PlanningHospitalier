

CREATE TABLE Personnel(
	idPersonnel INT AUTO_INCREMENT NOT NULL ,
	nom VARCHAR(20),
	prenom VARCHAR(20),
	dateNaissance DATE, 
	tempsTravailMensuel INT, 
	photo VARCHAR(50),
	nomFonction VARCHAR(20),
	PRIMARY KEY(idPersonnel)
);

CREATE TABLE specialise(
	idPersonnel INT NOT NULL,
	nomSpecialite VARCHAR(20) NOT NULL,
	PRIMARY KEY(idPersonnel, nomSpecialite)
);

CREATE TABLE Specialite(
	nom VARCHAR(20) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY(nom)
);

CREATE TABLE lie(
	nomFonction VARCHAR(20) NOT NULL,
	nomSpecialite VARCHAR(20) NOT NULL,
	PRIMARY KEY(nomFonction, nomSpecialite)
);

CREATE TABLE Fonction(
	nom VARCHAR(20) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY(nom)
);

CREATE TABLE travaille(
	idCreneaux INT NOT NULL,
	idPersonnel INT NOT NULL,
	PRIMARY KEY(idCreneaux, idPersonnel)
);

CREATE TABLE Creneaux(
	idCreneaux INT AUTO_INCREMENT NOT NULL,
	dateCreneaux DATE,
	heureDebut TIME,
	heureFin TIME,
	duree FLOAT,
	PRIMARY KEY(idCreneaux)
);

CREATE TABLE Contrainte(
	idContrainte INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(20),
	activation boolean,
	violation boolean,
	description VARCHAR(255),
	PRIMARY KEY(idContrainte)
);

CREATE TABLE besoin(
	idCreneaux INT NOT NULL,
	nomSpecialite VARCHAR(20) NOT NULL,
	nbPersonnel INT,
	PRIMARY KEY(idCreneaux, nomSpecialite)
);

ALTER TABLE Personnel
ADD FOREIGN KEY (nomFonction) 
REFERENCES Fonction(nom); 

ALTER TABLE specialise
ADD FOREIGN KEY (idPersonnel) REFERENCES Personnel(idPersonnel),
ADD FOREIGN KEY (nomSpecialite) REFERENCES Specialite(nom);

ALTER TABLE lie
ADD FOREIGN KEY (nomFonction) REFERENCES Fonction(nom),
ADD FOREIGN KEY (nomSpecialite) REFERENCES Specialite(nom);

ALTER TABLE travaille
ADD FOREIGN KEY (idCreneaux) 
REFERENCES Creneaux(idCreneaux),
ADD FOREIGN KEY (idPersonnel) 
REFERENCES Personnel(idPersonnel);

ALTER TABLE besoin
ADD FOREIGN KEY (idCreneaux) 
REFERENCES Creneaux(idCreneaux),
ADD FOREIGN KEY (nomSpecialite) 
REFERENCES Specialite(nom);

INSERT INTO specialite (nom)VALUES ("Cardiologie"),("Pediatrie"),("Gynecologie");

INSERT INTO fonction (nom) VALUES ("Docteur"), ("Infirmier"), ("Chirurgien"), ("Sage-femme"), ("Stagiaire");

