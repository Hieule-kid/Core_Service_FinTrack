package com.fintrack.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Unified API response envelope for all FinTrack REST endpoints.
 *
 * <p>Every endpoint returns this wrapper to ensure a consistent shape
 * that the frontend can rely on:
 * <pre>{@code
 * {
 *   "code":      200,
 *   "message":   "OK",
 *   "data":      { ... },
 *   "timestamp": "2026-04-26T10:00:00"
 * }
 * }</pre>
 *
 * <p>Fields with {@code null} values are omitted from the JSON output
 * ({@code @JsonInclude(NON_NULL)}).
 *
 * @param <T> the type of the {@code data} payload
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;

    private String message;

    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("OK")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .code(201)
                .message("Created")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<Void> error(int code, String message) {
        return ApiResponse.<Void>builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
