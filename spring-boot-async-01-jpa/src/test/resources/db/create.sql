-- Create Table Job
Create Table job
      (id integer Not Null,
       start_date timestamp Not Null,
       end_date timestamp,
       status character varying(100) Not Null,
       message text,
       task_name character varying(100) Not Null,
       task_detail character varying(250) Not Null);

-- Primary Key Documento
Alter Table job Add Constraint job_pk Primary Key (id);

create sequence seq_Id_job;
