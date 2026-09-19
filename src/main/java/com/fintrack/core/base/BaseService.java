package com.fintrack.core.base;

import com.fintrack.core.dto.PageResponse;

/**
 * Generic CRUD service contract for all FinTrack services.
 *
 * <p>All business service interfaces should extend this interface
 * to guarantee consistent CRUD behaviour across the platform.
 *
 * <p>Type parameters:
 * <ul>
 *   <li>{@code C} — Create DTO (inbound, validated request body)</li>
 *   <li>{@code U} — Update DTO (inbound, validated request body)</li>
 *   <li>{@code R} — Response DTO (outbound, safe view of the resource)</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>{@code
 * public interface TransactionService extends BaseService<CreateTransactionRequest,
 *                                                          UpdateTransactionRequest,
 *                                                          TransactionResponse> {
 *     // additional domain-specific methods here
 * }
 * }</pre>
 *
 * @param <C> create request DTO type
 * @param <U> update request DTO type
 * @param <R> response DTO type
 */
public interface BaseService<C, U, R> {

    R create(C createRequest);

    R update(String id, U updateRequest);

    R findById(String id);

    PageResponse<R> findAll(int page, int size, String sortBy, String sortDir);

    void delete(String id);
}
