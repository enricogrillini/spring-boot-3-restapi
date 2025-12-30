-- Create Table Job
Create Table job
      (id integer Not Null,
       task_name character varying(100) Not Null,
       task_detail character varying(250) Not Null,
       start_date timestamp Not Null,
       status character varying(100));

-- Primary Key Documento
Alter Table job Add Constraint job_pk Primary Key (id);

create sequence seq_Id_job;
