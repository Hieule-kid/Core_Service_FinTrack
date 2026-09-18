# FinTrack :: Core

Shared library for the FinTrack microservices: `BaseEntity`/`BaseService`/`BaseController` generic CRUD scaffolding, `ApiResponse`/`PageResponse` envelopes, `ErrorCode`/`AppException`/`GlobalExceptionHandler` for unified error handling, and `DateUtils`.

This is a plain Maven **library** (`packaging=jar`, no `spring-boot-maven-plugin` repackaging) — it is not a runnable service.

## Consumers

`auth-service` ([Auth_Service_FinTrack](https://github.com/Hieule-kid/Auth_Service_FinTrack)) and `planning-service` ([Planning_Service_FinTrack](https://github.com/Hieule-kid/Planning_Service_FinTrack)) depend on this library via a **git submodule** checked out at `core/` in their own repos, built together with their own module in the same Maven reactor (`mvnw package -pl <service> -am`). `config-service` does not depend on `core`.

There is no published artifact registry for `core` — consumers always build it from source via the submodule. When you change `core`:

1. Commit and push the change here.
2. In each consuming repo, `cd core && git pull origin main` (or `git submodule update --remote`), then commit the updated submodule pointer.

## Build

```bash
./mvnw clean install -DskipTests
```

## Versions

Java 17, Spring Boot 3.4.4, MapStruct 1.6.2 — kept in sync by hand with the same versions used in `Auth_Service_FinTrack` and `Planning_Service_FinTrack` (no shared parent POM anymore since the repo split).
