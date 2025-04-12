package com.hsoft.model.dto.v1.products;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing product ID and current stock level.")
public class ProductStockResponseDTO {
    @Schema(description = "Unique identifier of the product.")
    private UUID productId;
    @Schema(description = "Current stock quantity.")
    private int currentStock;
}