package com.springapi.orders.presentation.globalExceptionHandler;

import java.time.Instant;

public record ApiErrorResponse(
        String message,
        int code,
        Instant timestamp
) {}
