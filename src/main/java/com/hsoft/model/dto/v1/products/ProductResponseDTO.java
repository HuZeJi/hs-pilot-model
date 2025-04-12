package com.hsoft.model.dto.v1.products;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed representation of a product.")
public class ProductResponseDTO {
    @Schema(description = "Unique identifier of the product.", example = "b2c3d4e5-f6a7-8901-2345-67890abcdef0", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID productId;

    @Schema(description = "ID of the main user account owning this product.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Stock Keeping Unit.", example = "PROD-001")
    private String sku;

    @Schema(description = "Name of the product.", example = "Widget Deluxe")
    private String name;

    @Schema(description = "Detailed description.", example = "A very fine widget.")
    private String description;

    @Schema(description = "Purchase price.", example = "10.50")
    private BigDecimal purchasePrice;

    @Schema(description = "Sale price.", example = "25.00")
    private BigDecimal salePrice;

    @Schema(description = "Current stock quantity.", example = "50")
    private int currentStock;

    @Schema(description = "Unit of measure.", example = "unit")
    private String unitOfMeasure;

    @Schema(description = "Product category.", example = "Widgets")
    private String category;

    @Schema(description = "Active status.", example = "true")
    private boolean isActive;

    @Schema(description = "Timestamp when created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Schema(description = "Custom data.", example = "{\"color\": \"blue\", \"size\": \"large\"}")
    private Map<String, Object> context;
}
