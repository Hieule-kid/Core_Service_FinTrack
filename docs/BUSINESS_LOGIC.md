# Business Logic & Cross-Service Conventions

This document captures business rules and cross-service flow knowledge that used
to live as inline comments in the shared `core` library. Since this module's
classes (`BaseEntity`, `BaseService`, `BaseController`, `ErrorCode`,
`AppException`, `GlobalExceptionHandler`, ...) are reused by every FinTrack
microservice (Auth Service, Core Service, Planning Service), the rules below
apply platform-wide, not just to this repo. Treat this file as the source of
truth for these rules instead of scattered code comments.

## Data auditing (BaseEntity)

- `BaseEntity` populates `createdAt`, `updatedAt`, `createdBy`, and `updatedBy`
  automatically via Spring Data JPA auditing. For this to actually work, each
  service's main application class must enable `@EnableJpaAuditing` — without
  it, the audit fields silently stay `null` (see `BaseEntity.java`).
- Populating `createdBy` / `updatedBy` additionally requires each service to
  provide its own `AuditorAware<String>` bean (typically resolving the current
  authenticated user's ID). Services that skip this bean will get audit
  timestamps but no audit user (see `BaseEntity.java`).

## Soft delete convention

- All entities extending `BaseEntity` use a soft-delete model: deleting a
  resource sets the `deleted` flag to `true` instead of physically removing
  the row. This preserves audit trails across the platform (see
  `BaseEntity.java`, field `deleted`).
- Repository queries across all services must filter by `deleted = false` to
  exclude soft-deleted rows from normal results (see `BaseEntity.java`).
- The `deleted` flag should never be set directly on the entity — it must
  only be changed through the service layer's `delete()` method, which is the
  single place responsible for enforcing the soft-delete flow (see
  `BaseEntity.java`, field `deleted`; `BaseService.java`, method `delete`).

## Error handling flow (AppException / GlobalExceptionHandler)

- `AppException` is the base runtime exception for business-rule violations.
  The convention across all FinTrack services is: throw `AppException` (or a
  subclass) from the service layer whenever a business rule is violated, and
  let the shared `GlobalExceptionHandler` translate it into the appropriate
  HTTP response (see `AppException.java`).
- For `GlobalExceptionHandler` to actually intercept exceptions, each
  service's main application class must scan the `com.fintrack` base package
  (e.g. `@SpringBootApplication(scanBasePackages = "com.fintrack")`).
  Services that don't do this will not get the shared error-handling/response
  shape (see `GlobalExceptionHandler.java`).
- Unhandled/unexpected exceptions are caught by a catch-all handler that logs
  the full stack trace server-side but always returns a generic
  `INTERNAL_SERVER_ERROR` message to the client — stack traces and internal
  error details must never be exposed to API clients (see
  `GlobalExceptionHandler.java`, method `handleGenericException`).

## Error code convention (ErrorCode)

`ErrorCode` values are allocated by numeric range so that the meaning of a
code is consistent across every FinTrack service and the frontend can map
codes to localized messages without ambiguity (see `ErrorCode.java`):

- `1xxx` — Authentication / Authorization errors
- `2xxx` — Resource not found errors
- `3xxx` — Validation / business rule errors
- `4xxx` — Planning / budgeting domain errors
- `5xxx` — Server / infrastructure errors

New error codes added by any service should follow this same range
convention rather than reusing/overlapping another range.

## Timezone convention (DateUtils)

- `DateUtils.nowVietnam()` exists because FinTrack's primary user base is
  Vietnam-based; several user-facing date computations use
  `Asia/Ho_Chi_Minh` as the reference local timezone rather than server/UTC
  time (see `DateUtils.java`, method `nowVietnam`).
