package com.jeontongju.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseFormat<T> {

    private T content;
    private Long totalPage;
    private Integer size;
    private Boolean first;
    private Boolean last;

    public static <T> PageResponseFormat<T> toDto(Long totalHits, Pageable pageable, T content) {

        Long totalPage = (long) Math.ceil((double) totalHits / pageable.getPageSize());

        return PageResponseFormat.<T>builder()
                .content(content)
                .first(pageable.getPageNumber() == 0)
                .last(pageable.getPageNumber() == totalPage )
                .totalPage(totalPage)
                .size(pageable.getPageSize())
                .build();
    }
}
