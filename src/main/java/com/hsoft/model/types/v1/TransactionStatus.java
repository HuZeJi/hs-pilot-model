package com.hsoft.model.types.v1;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of the transaction.")
public enum TransactionStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}
