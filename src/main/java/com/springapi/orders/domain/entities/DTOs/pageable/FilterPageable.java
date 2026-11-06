package com.springapi.orders.domain.entities.DTOs.pageable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterPageable {
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
    @NotNull
    private Direction direction;
    @NotNull
    private String orderBy;
}
