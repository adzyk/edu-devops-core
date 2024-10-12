Базовые компоненты обучающего портала DevOps-практик
====================================================

Данный проект содержит базовые компоненты для построения обучающего портала DevOps-практикам.

Стек
----

1. API Server
   * Java 21, Spring Boot 3
2. Engine (Docker)
   * Golang 1.21
3. Frontend
   * Vue.js, axios


Связанные сервисы
-----------------

1. Keycloak
2. PostgreSQL
3. Kafka


Сборка
------

```shell
cd src/backend/api-server
docker build -t api-server .
```

```shell
cd src/backend/engines/docker
docker build -t docker-engine .
```

```shell
cd src/frontend/user-frontend
docker build -t user-frontend .
```

Локальная разработка
--------------------

Для разработки необходимо [запустить](./debug/Readme.md) связанные сервисы.


Лицензия
--------

Данный проект распространяется под лицензией [Apache 2.0](./LICENSE)