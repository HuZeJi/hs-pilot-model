package com.hsoft.model.dto.v1.transactions;

import com.hsoft.model.types.v1.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for updating limited fields of a transaction.")
public class TransactionUpdateRequestDTO {
    // Only include fields that make sense to update post-creation
    @Schema(description = "Updated status (e.g., to CANCELLED).", example = "CANCELLED")
    private TransactionStatus status;

    @Schema(description = "Updated notes.")
    private String notes;

    @Size(max = 100)
    @Schema(description = "Updated reference number.")
    private String referenceNumber; // Maybe allow update?

    @Schema(description = "Updated custom data.", example = "{\"cancellation_reason_code\": \"C01\"}")
    private Map<String, Object> context;

    // Generally, client/provider/items/total are NOT updated via PATCH.
    // Create adjustments or new transactions instead.
}
