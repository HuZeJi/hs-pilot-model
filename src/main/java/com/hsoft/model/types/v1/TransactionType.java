package com.hsoft.model.types.v1;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of transaction (Sale or Purchase).")
public enum TransactionType {
    SALE,
    PURCHASE
}
