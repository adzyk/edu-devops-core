ARG LIQUIBASE_VERSION=4.9

FROM liquibase/liquibase:${LIQUIBASE_VERSION}
ADD src/main/resources /liquibase/changelog
ENTRYPOINT ["/liquibase/docker-entrypoint.sh", "--changeLogFile=db.root-master.xml"]
