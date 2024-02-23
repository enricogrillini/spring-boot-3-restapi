-- Create Table Documento
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

Insert into Documento
       (Id,                          Nome,        Descrizione,                                                Data,         Update_By)
Values (nextval('seq_Id_Documento'), 'Contratto', 'Contratto tra le parti per sottoscrizione conto corrente', '2024-01-01', 'writer-1'),
       (nextval('seq_Id_Documento'), 'Recesso',  'Norme per il recesso',                                      '2024-01-01', 'writer-1'),
       (nextval('seq_Id_Documento'), 'Appendice',  'Appendice al contratto di sottoscrizione',                '2024-01-01', 'writer-1');
