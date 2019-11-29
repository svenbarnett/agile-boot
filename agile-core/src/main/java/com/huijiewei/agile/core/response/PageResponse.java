package com.huijiewei.agile.core.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

public class PageResponse<E> extends ListResponse<E> {
    @Schema(description = "分页信息")
    private Pagination pagination;

    public Pagination getPages() {
        return this.pagination;
    }

    public void setPage(Page<E> page) {
        this.setItems(page.getContent());

        Pagination pages = new Pagination();
        pages.currentPage = page.getNumber() + 1;
        pages.totalCount = page.getTotalElements();
        pages.pageCount = page.getTotalPages();
        pages.perPage = page.getSize();

        this.pagination = pages;
    }

    @Data
    private static class Pagination {
        private Long totalCount;
        private Integer currentPage;
        private Integer pageCount;
        private Integer perPage;
    }
}
