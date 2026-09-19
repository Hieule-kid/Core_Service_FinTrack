package com.fintrack.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Pagination response wrapper returned by all paginated list endpoints.
 *
 * <p>Wraps a {@link Page} result from Spring Data into a FinTrack-standard DTO
 * so the shape is decoupled from Spring internals:
 * <pre>{@code
 * {
 *   "content":       [ ... ],
 *   "pageNumber":    0,
 *   "pageSize":      10,
 *   "totalElements": 42,
 *   "totalPages":    5,
 *   "last":          false
 * }
 * }</pre>
 *
 * @param <T> the element type of the content list
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
