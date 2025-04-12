package com.hsoft.model.dto.v1.products;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Request body for creating a new product.")
public class ProductCreateRequestDTO {
    @Size(max = 100)
    @Schema(description = "Stock Keeping Unit (unique per user).", example = "PROD-001")
    private String sku;

    @NotBlank @Size(max = 255)
    @Schema(description = "Name of the product.", example = "Widget Deluxe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Detailed description of the product.", example = "A very fine widget.")
    private String description;

    @Digits(integer = 8, fraction = 2) @PositiveOrZero
    @Schema(description = "Price paid to the provider.", example = "10.50")
    private BigDecimal purchasePrice;

    @NotNull @Digits(integer = 8, fraction = 2) @PositiveOrZero
    @Schema(description = "Price charged to the client.", example = "25.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal salePrice;

    @PositiveOrZero
    @Schema(description = "Initial stock quantity.", example = "50")
    private Integer currentStock = 0; // Default if not provided

    @Size(max = 50)
    @Schema(description = "Unit for measuring stock.", example = "unit")
    private String unitOfMeasure;

    @Size(max = 100)
    @Schema(description = "Product category.", example = "Widgets")
    private String category;

    @Schema(description = "Initial active status.", example = "true")
    private Boolean isActive = true; // Default if not provided

    @Schema(description = "Custom data for the product.", example = "{\"color\": \"blue\", \"size\": \"large\"}")
    private Map<String, Object> context;
}
