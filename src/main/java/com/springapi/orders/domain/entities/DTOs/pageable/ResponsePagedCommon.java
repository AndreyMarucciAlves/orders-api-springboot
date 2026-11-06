package com.springapi.orders.domain.entities.DTOs.pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponsePagedCommon <T>{
    private List<T> data;
    private Long totalRecords;
    private int totalPages;
    private int pageSize;
}
