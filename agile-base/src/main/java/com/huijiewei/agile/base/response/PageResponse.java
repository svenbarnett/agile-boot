package com.huijiewei.agile.base.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<E> extends ListResponse<E> {
    private Pagination pages;

    public PageResponse<E> data(Page<E> page) {
        this.setItems(page.getContent());

        Pagination pages = new Pagination();
        pages.currentPage = page.getNumber();
        pages.totalCount = page.getTotalElements();
        pages.pageCount = page.getTotalPages();
        pages.perPage = page.getSize();

        this.setPages(pages);

        return this;
    }

    @Data
    public static class Pagination {
        private Long totalCount;
        private Integer currentPage;
        private Integer pageCount;
        private Integer perPage;
    }
}
