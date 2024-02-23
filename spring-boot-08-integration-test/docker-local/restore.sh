psql -U postgres -d postgres -c "DROP SCHEMA IF EXISTS cookbook CASCADE"
psql -U postgres -d postgres -c "DROP ROLE IF EXISTS cookbook"

psql -U postgres -d postgres -c "CREATE ROLE cookbook WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION PASSWORD 'cookbook'"
psql -U postgres -d postgres -c "ALTER ROLE cookbook SET search_path TO cookbook"
psql -U postgres -d postgres -c "ALTER ROLE cookbook IN DATABASE postgres SET search_path TO cookbook"
psql -U postgres -d postgres -c "CREATE SCHEMA cookbook AUTHORIZATION cookbook"

pg_restore -h localhost -U postgres -d postgres -n cookbook < /opt/backup.dmp