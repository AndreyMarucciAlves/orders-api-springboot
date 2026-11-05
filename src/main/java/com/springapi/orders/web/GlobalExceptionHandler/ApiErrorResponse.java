package com.springapi.orders.web.GlobalExceptionHandler;

import java.time.Instant;

public record ApiErrorResponse(
        String message,
        int code,
        Instant timestamp
) {}
