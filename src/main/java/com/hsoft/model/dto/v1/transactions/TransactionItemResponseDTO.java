package com.hsoft.model.dto.v1.transactions;

import com.hsoft.model.dto.v1.commons.ProductSummaryResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Representation of a transaction item within a detailed transaction view.")
public class TransactionItemResponseDTO {
    @Schema(description = "Unique identifier of the transaction item.", example = "f6a7b8c9-d0e1-2345-6789-0abcdef01234", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID itemId;

    @NotNull // Include product summary
    @Schema(description = "Summary of the product involved.")
    private ProductSummaryResponseDTO product;

    @Schema(description = "Quantity of the product.", example = "2")
    private int quantity;

    @Schema(description = "Price per unit at the time of transaction.", example = "25.00")
    private BigDecimal unitPrice;

    @Schema(description = "Subtotal for this line item (quantity * unitPrice).", example = "50.00", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal subtotal;

    @Schema(description = "Custom data associated with this item.", example = "{\"discount_applied\": \"5%\"}")
    private Map<String, Object> context;
}
