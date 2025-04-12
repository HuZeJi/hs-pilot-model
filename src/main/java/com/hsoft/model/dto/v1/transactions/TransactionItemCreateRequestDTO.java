package com.hsoft.model.dto.v1.transactions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for creating a transaction item.")
public class TransactionItemCreateRequestDTO {
    @NotNull
    @Schema(description = "ID of the product being transacted.", example = "b2c3d4e5-f6a7-8901-2345-67890abcdef0", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID productId;

    @NotNull @Min(1) // Or allow negative for returns? Depends on logic. Check constraint is != 0
    @Schema(description = "Quantity of the product.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotNull @Digits(integer = 8, fraction = 2) @PositiveOrZero
    @Schema(description = "Price per unit at the time of transaction.", example = "25.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    @Schema(description = "Custom data for this transaction item.", example = "{\"discount_applied\": \"5%\"}")
    private Map<String, Object> context;
}
