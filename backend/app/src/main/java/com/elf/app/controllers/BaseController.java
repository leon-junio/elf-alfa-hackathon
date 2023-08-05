package com.elf.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
public class BaseController {
    protected static final String API_VERSION = "v1";
    protected static final String API_PATH = "api";
    @Value("${elf.default-page-size}")
    protected int DEFAULT_PAGE_SIZE;
    protected static final int DEFAULT_FIRST_PAGE = 0;

    protected Pageable paginate(Integer page, Integer perPage, String sort) {
        Pageable pageable = PageRequest
                .of(page == null ? DEFAULT_FIRST_PAGE : page, perPage == null ? DEFAULT_PAGE_SIZE : perPage);
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            pageable = PageRequest.of(page == null ? DEFAULT_FIRST_PAGE : page,
                    perPage == null ? DEFAULT_PAGE_SIZE : perPage, Sort.by(sortParams));
        }
        return pageable;
    }
}
