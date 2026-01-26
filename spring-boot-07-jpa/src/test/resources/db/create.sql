-- Create Table Documento
Create Table Documento
      (Id integer Not Null,
       Nome character varying(100) Not Null,
       Descrizione character varying(250) Not Null,
       Data date Not Null,
       Update_By character varying(100));

-- Primary Key Documento
Alter Table Documento Add Constraint documento_pk Primary Key (Id);

create sequence seq_Id_Documento;

-- Create Table Autore
Create Table Autore
      (Id integer Not Null,
       Nome character varying(100) Not Null,
       Cognome character varying(250) Not Null);

-- Primary Key Documento
Alter Table Autore Add Constraint autore_pk Primary Key (Id);

create sequence seq_Id_Autore;

-- Create Table Documento_Autore
Create Table Documento_Autore
      (Id_Documento_Autore integer Not Null,
       Id_Documento integer Not Null,
       Id_Autore integer Not Null);

-- Primary Key Documento
Alter Table Documento_Autore Add Constraint Documento_Autore_pk Primary Key (Id_Documento_Autore);
Alter Table Documento_Autore Add Constraint Documento_Autore_fk1 Foreign Key (Id_Documento) References Documento;
Alter Table Documento_Autore Add Constraint Documento_Autore_fk2 Foreign Key (Id_Autore) References Autore;
Alter Table Documento_Autore Add Constraint Documento_Autore_uk1 Unique (Id_Documento, Id_Autore);

create sequence seq_Id_Documento_Autore;


-- DATI
Insert into Documento
       (Id,                          Nome,        Descrizione,                                                Data,         Update_By)
Values (nextval('seq_Id_Documento'), 'Contratto', 'Contratto tra le parti per sottoscrizione conto corrente', '2024-01-01', 'writer-a'),
       (nextval('seq_Id_Documento'), 'Recesso',  'Norme per il recesso',                                      '2024-01-01', 'writer-a'),
       (nextval('seq_Id_Documento'), 'Appendice',  'Appendice al contratto di sottoscrizione',                '2024-01-01', 'writer-a');

Insert into Autore
       (Id,                          Nome,        Cognome)
Values (nextval('seq_Id_Autore'),    'Mario',     'Rossi'),
       (nextval('seq_Id_Autore'),    'Ugo',       'Bianchi');

Insert into Documento_Autore
       (Id_Documento_Autore,                 Id_Documento,    Id_Autore)
Values (nextval('seq_Id_Documento_Autore'),  1,               1);
