CREATE TABLE Administrator (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255),
    email VARCHAR(255),
    name  VARCHAR(255),
    lastName VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255)
);

CREATE TABLE Organizator (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255),
    email VARCHAR(255),
    name  VARCHAR(255),
    lastName VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    profileApproved BOOLEAN
);

CREATE TABLE Korisnik (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255),
    email VARCHAR(255),
    name  VARCHAR(255),
    lastName VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    walletBalance INT,
    brojKupljenihKarti INT,
    profileApproved BOOLEAN
);

CREATE TABLE Lokacija (
	lokacija_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    naziv VARCHAR(255),
    kapacitet INT,
    grad VARCHAR(255),
    adresa VARCHAR(255),
    slika VARCHAR(255)
);

CREATE TABLE Sektor (
	sektor_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    naziv VARCHAR(255),
    kapacitet INT,
    lokacija_id INT,
    FOREIGN KEY (lokacija_id) REFERENCES Lokacija(lokacija_id) ON DELETE CASCADE
);

CREATE TABLE Dogadjaj (
	dogadjaj_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    naziv VARCHAR(255),
    opis VARCHAR(255),
    datum TIMESTAMP,
    vrsta VARCHAR(255),
    podvrsta VARCHAR(255),
    slika VARCHAR(255),
    zavrsio BOOLEAN,
    maxKartiPoKorisniku INT,
    naplataPriRezervaciji BOOLEAN,
    dogadjajApproved BOOLEAN,
    organizator_id VARCHAR(255),
    lokacija_id INT,
    FOREIGN KEY (organizator_id) REFERENCES Organizator(username) ON DELETE NO ACTION,
    FOREIGN KEY (lokacija_id) REFERENCES Lokacija(lokacija_id) ON DELETE NO ACTION
);

CREATE TABLE Izmjene (
	imeTabele VARCHAR(255),
    stringIdEntitija VARCHAR(255),
    imeKolone VARCHAR(255),
    novaVrijednost VARCHAR(255),
    CONSTRAINT IzmjeneId PRIMARY KEY (imeTabele,stringIdEntitija,imeKolone)
);

CREATE TABLE Karta (
	karta_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    cijena INT,
    cijenaRezervacije INT,
    sjediste VARCHAR(255),
    sektor_id INT,
    dogadjaj_id INT,
    korRezervisao VARCHAR(255),
    korKupio VARCHAR(255),
    FOREIGN KEY (sektor_id) REFERENCES Sektor(sektor_id) ON DELETE NO ACTION,
    FOREIGN KEY (dogadjaj_id) REFERENCES Dogadjaj(dogadjaj_id) ON DELETE NO ACTION,
    FOREIGN KEY (korRezervisao) REFERENCES Korisnik(username) ON DELETE NO ACTION,
    FOREIGN KEY (korKupio) REFERENCES Korisnik(username) ON DELETE NO ACTION
);