# FinTrack :: Core

Shared library for the FinTrack microservices: `BaseEntity`/`BaseService`/`BaseController` generic CRUD scaffolding, `ApiResponse`/`PageResponse` envelopes, `ErrorCode`/`AppException`/`GlobalExceptionHandler` for unified error handling, and `DateUtils`.

This is a plain Maven **library** (`packaging=jar`, no `spring-boot-maven-plugin` repackaging) — it is not a runnable service.

## Consumers

`auth-service` ([Auth_Service_FinTrack](https://github.com/Hieule-kid/Auth_Service_FinTrack)) and `planning-service` ([Planning_Service_FinTrack](https://github.com/Hieule-kid/Planning_Service_FinTrack)) depend on `com.fintrack:core` as an ordinary Maven dependency, resolved from **GitHub Packages** (`https://maven.pkg.github.com/Hieule-kid/Core_Service_FinTrack`). There is no git submodule and no vendored/copied source anywhere anymore — each service repo is fully self-contained and builds independently. `config-service` does not depend on `core`.

## Publishing a change

GitHub Packages requires authentication to publish, even to a public repo:

1. Create a classic PAT with the `write:packages` scope.
2. Export it: `export GITHUB_ACTOR=<your-github-username> GITHUB_TOKEN=<your-pat>`.
3. Bump `<version>` in `pom.xml` if this is a real release (don't silently overwrite a version consumers already pinned).
4. `./mvnw -s settings.xml clean deploy -DskipTests`
5. Update `fintrack.version` in `Auth_Service_FinTrack/pom.xml` and `Planning_Service_FinTrack/pom.xml` to match, and rebuild those repos.

`settings.xml` (committed here, no secrets in it) wires `${env.GITHUB_ACTOR}`/`${env.GITHUB_TOKEN}` into Maven's `github` server credentials used by `<distributionManagement>` in `pom.xml`.

## Build

```bash
./mvnw clean install -DskipTests
```

## Versions

Java 17, Spring Boot 3.4.4, MapStruct 1.6.2 — kept in sync by hand with the same versions used in `Auth_Service_FinTrack` and `Planning_Service_FinTrack` (no shared parent POM anymore since the repo split).
