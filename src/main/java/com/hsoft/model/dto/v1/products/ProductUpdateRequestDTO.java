package com.hsoft.model.dto.v1.products;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for updating an existing product.")
public class ProductUpdateRequestDTO {
    // Include only fields allowed to be updated
    @Size(max = 100)
    @Schema(description = "Updated Stock Keeping Unit.", example = "PROD-001-MOD")
    private String sku;

    @Size(max = 255)
    @Schema(description = "Updated product name.", example = "Widget Deluxe v2")
    private String name;

    @Schema(description = "Updated description.")
    private String description;

    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    @Schema(description = "Updated purchase price.", example = "11.00")
    private BigDecimal purchasePrice;

    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    @Schema(description = "Updated sale price.", example = "26.50")
    private BigDecimal salePrice;

    @Size(max = 50)
    @Schema(description = "Updated unit of measure.", example = "piece")
    private String unitOfMeasure;

    @Size(max = 100)
    @Schema(description = "Updated category.", example = "Deluxe Widgets")
    private String category;

    @Schema(description = "Updated custom data.", example = "{\"color\": \"red\"}")
    private Map<String, Object> context;

    // Note: currentStock is usually updated via transactions or specific stock adjustments
    // Note: isActive is usually updated via the dedicated status endpoint
}
