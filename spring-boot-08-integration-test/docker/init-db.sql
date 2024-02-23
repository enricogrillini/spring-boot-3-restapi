CREATE ROLE cookbook WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION PASSWORD 'cookbook';
ALTER ROLE cookbook SET search_path TO cookbook;
ALTER ROLE cookbook IN DATABASE postgres SET search_path TO cookbook;
CREATE SCHEMA cookbook AUTHORIZATION cookbook;

set schema 'cookbook';

Create Sequence Seq_IdDocument;

-- Create Table Label
Create Table Document
      (Id integer Not Null,
       name VARCHAR(100) Not Null,
       description VARCHAR(100) Not Null,
       data date,
       update_by VARCHAR(100));

-- Primary Key Document
Alter Table Document Add Constraint Document_PK Primary Key (id);


Insert into Document values (nextval('Seq_IdDocument'), 'doc-1', 'Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;
Insert into Document values (nextval('Seq_IdDocument'), 'doc-2', 'Allegato 1 - Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;
Insert into Document values (nextval('Seq_IdDocument'), 'doc-3', 'Allegato 3 - Contratto vita', to_date('01/01/2022','dd/MM/yyyy'), 'Ugo') ;


