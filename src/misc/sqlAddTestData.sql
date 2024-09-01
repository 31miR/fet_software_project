INSERT INTO Administrator VALUES ('riot123',
	'aram123',
    'support@riotgames.com',
    'Dylan',
    'Jadeja',
    'Ulica tamo negdje b.b., Tuzla',
    '38760225883'
);
INSERT INTO Organizator VALUES ('amer_hasanovic',
	'vimjezakon123',
    'amer@linux.com',
    'Amer',
    'Hasanovic',
    'nesto nesto, Tuzla',
    '123456789',
    true
);
INSERT INTO Organizator VALUES ('rostilj123',
	'kobasice123',
    'rostilj123@gmail.com',
    'Imen',
    'Prezimen',
    'nesto nesto, Tuzla',
    '123456788',
    true
);
INSERT INTO Korisnik VALUES ('elon_musk',
	'teslaGoZoom',
    'realelon123@gmail.com',
    'Elon',
    'Musk',
    'Vida II, Gradacac',
    '123456788',
    1000000000,
    0,
    true
);
INSERT INTO Lokacija VALUES (DEFAULT,
	'Mejdan',
	10000,
    'Tuzla',
    'Bosne Srebrene',
    'lokacijaImages/1.jpg'
);
INSERT INTO Sektor VALUES (DEFAULT,
	'velika dvorana',
	8000,
    1
);
INSERT INTO Sektor VALUES (DEFAULT,
	'mala dvorana',
	2000,
    1
);
INSERT INTO Lokacija VALUES (DEFAULT,
	'Bingo City Center - Cinestar',
	400,
    'Tuzla',
    'Mitra Trifunovica Uce 2',
    'lokacijaImages/2.jpg'
);
INSERT INTO Sektor VALUES (DEFAULT,
	'dvorana 1',
	92,
    2
);
INSERT INTO Sektor VALUES (DEFAULT,
	'dvorana 2',
	92,
    2
);
INSERT INTO Sektor VALUES (DEFAULT,
	'dvorana 3',
	92,
    2
);
INSERT INTO Lokacija VALUES (DEFAULT,
	'Skenderija',
	1000,
    'Gradačac',
    'Husein-kapetana Grascevica',
    'lokacijaImages/3.jpg'
);
INSERT INTO Sektor VALUES (DEFAULT,
	'skenderija',
	1000,
    3
);
INSERT INTO Lokacija VALUES (DEFAULT,
	'Narodno Pozoriste',
	100,
    'Tuzla',
    'Pozorisna 4',
    'lokacijaImages/4.jpg'
);
INSERT INTO Sektor VALUES (DEFAULT,
	'pozoriste',
	100,
	4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Rupert Holmes i pinje kolade',
	'Poznati pjevac Rupert Holmes odrzat ce koncert u nasem gradu i pitati vas da li vi volite pinje kolade',
    '2024-11-09 22:00:00',
    'Muzika',
    'Koncert',
    'dogadjajImages/1.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'amer_hasanovic',
    1
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Voyage i Baka Prase - MeetUp Gradacac',
	'Popularni youtuber Baka Prase i popularni pjevac Voyage odrzat ce MeetUp u Gradaccu - Skenderija. Na njima ce sve biti VVS.',
    '2023-11-09 22:00:00',
    'Skup',
    'Fan MeetUp',
    'dogadjajImages/2.jpg',
    TRUE,
    1,
    FALSE,
    TRUE,
    'amer_hasanovic',
    3
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 6 patuljaka - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Jedan glumac je bolestan pa je zato 6 patuljaka.',
    '2024-11-22 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 5 patuljaka - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Dva glumca su bolesna pa je zato 5 patuljaka.',
    '2024-11-23 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 4 patuljka - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Tri glumca su bolesna pa je zato 4 patuljka.',
    '2024-11-24 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 3 patuljka - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Cetiri glumca su bolesna pa je zato 3 patuljka.',
    '2024-11-25 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 2 patuljka - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Pet glumaca je bolesno pa je zato 2 patuljka.',
    '2024-11-26 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Dogadjaj VALUES (DEFAULT,
	'Snjeguljica i 1 patuljak - Predstava',
	'Odrzava se predstava koja je kao spin-off Snjeguljice i 7 patuljaka. Sest glumaca je bolesno pa je zato 1 patuljak.',
    '2024-11-27 22:30:00',
    'Predstava',
    'Drama',
    'dogadjajImages/3.jpg',
    FALSE,
    1,
    FALSE,
    TRUE,
    'rostilj123',
    4
);
INSERT INTO Karta VALUES (DEFAULT,
	2000,
	0,
    'neodredjeno',
    1,
    1,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	2000,
	0,
    'neodredjeno',
    1,
    1,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	2000,
	0,
    'neodredjeno',
    1,
    1,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	2000,
	0,
    'neodredjeno',
    1,
    1,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	2000,
	0,
    'neodredjeno',
    1,
    1,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	1000,
	500,
    'neodredjeno',
    6,
    2,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	1000,
	500,
    'neodredjeno',
    6,
    2,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	1000,
	500,
    'neodredjeno',
    6,
    2,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	1000,
	500,
    'neodredjeno',
    6,
    2,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	1000,
	500,
    'neodredjeno',
    6,
    2,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	500,
	500,
    'neodredjeno',
    7,
    3,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	500,
	500,
    'neodredjeno',
    7,
    3,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	500,
	500,
    'neodredjeno',
    7,
    3,
    null,
    null
);
INSERT INTO Karta VALUES (DEFAULT,
	500,
	500,
    'neodredjeno',
    7,
    3,
    null,
    null
);