Create Sequence Seq_IdDocument;

-- Create Table Label
Create Table Document
      (Id integer Not Null,
       name VARCHAR(100) Not Null,
       description VARCHAR(100) Not Null,
       data date,
       update_by VARCHAR(100)
      );

-- Primary Key Document
Alter Table Document Add Constraint Document_PK Primary Key (id);

-- Nota: Su H2 la funzione TO_DATE in modalità compatibilità PostgreSQL e' disponibile dalla v 2.0.204
Insert into Document values (nextval('Seq_IdDocument'), 'doc-1', 'Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;
Insert into Document values (nextval('Seq_IdDocument'), 'doc-2', 'Allegato 1 - Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;
Insert into Document values (nextval('Seq_IdDocument'), 'doc-3', 'Allegato 3 - Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;


