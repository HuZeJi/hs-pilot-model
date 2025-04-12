package com.hsoft.model.dto.v1.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// Reusable Summary for Products (e.g., in Transaction Items)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of a product.")
public class ProductSummaryResponseDTO {
    @Schema(description = "Unique identifier of the product.", example = "b2c3d4e5-f6a7-8901-2345-67890abcdef0")
    private UUID productId;

    @Schema(description = "SKU of the product.", example = "SKU-WIDGET-RED")
    private String sku;

    @Schema(description = "Name of the product.", example = "Red Widget")
    private String name;
}
