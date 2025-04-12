package com.hsoft.model.dto.v1.products;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for manually adjusting product stock.")
public class StockAdjustmentRequestDTO {
    @NotNull
    @Schema(description = "The change in stock quantity (positive for increase, negative for decrease).", example = "-5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer adjustment;

    @Schema(description = "Reason for the stock adjustment.", example = "Stocktake correction")
    private String reason;
}
