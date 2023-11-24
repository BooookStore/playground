FROM postgres:15

COPY 01-schema.sql /docker-entrypoint-initdb.d
COPY 02-data.sql /docker-entrypoint-initdb.d
