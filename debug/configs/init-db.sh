#!/bin/bash
set -e

# Keycloak
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER keycloak PASSWORD 'keycloak';
	CREATE DATABASE keycloak OWNER keycloak;
EOSQL

# API Server
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER education_system PASSWORD 'education_system';
	CREATE DATABASE education_system OWNER education_system;
EOSQL
