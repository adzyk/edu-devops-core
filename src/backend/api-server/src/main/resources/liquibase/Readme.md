База данных
===========


Создание структуры БД
---------------------

```shell
docker run \
  --network=education-system \
  --volume $PWD/src/main/resources/db:/liquibase/changelog \
  liquibase/liquibase:4.9 \
    --url="jdbc:postgresql://postgres:5432/education_system" \
    --changelog-file=./changelog/db.root-master.xml \
    --username=postgres \
    --password=postgres \
    update
```

Создание тестовых данных
------------------------

```shell
docker run \
  --network=education-system \
  --volume $PWD/src/main/resources/demo:/liquibase/changelog \
  liquibase/liquibase:4.9 \
    --url="jdbc:postgresql://postgres:5432/education_system" \
    --changelog-file=./changelog/demo.root-master.xml \
    --username=postgres \
    --password=postgres \
    update
```
