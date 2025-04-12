package com.hsoft.model.dto.v1.transactions;

import com.hsoft.model.types.v1.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for creating a new transaction (Sale or Purchase).")
public class TransactionCreateRequestDTO {
    // Type is usually determined by the endpoint (e.g., POST /transactions/sales)
    // but could be included if using a single endpoint

    @Schema(description = "ID of the client (Required for Sales).", example = "c3d4e5f6-a7b8-9012-3456-7890abcdef01")
    private UUID clientId; // Required if sale

    @Schema(description = "ID of the provider (Required for Purchases).", example = "d4e5f6a7-b8c9-0123-4567-890abcdef012")
    private UUID providerId; // Required if purchase

    @Schema(description = "Date and time of the transaction (defaults to now if omitted).", example = "2023-11-15T14:30:00Z")
    private OffsetDateTime transactionDate;

    @Size(max = 100)
    @Schema(description = "Reference number (Invoice No., PO No., etc.).", example = "INV-001")
    private String referenceNumber;

    @Schema(description = "Additional notes.")
    private String notes;

    @Schema(description = "Status of the transaction (defaults usually to COMPLETED).", example = "COMPLETED")
    private TransactionStatus status; // Default handled by server/DB

    @Schema(description = "Custom data for the transaction.", example = "{\"sales_channel\": \"web\"}")
    private Map<String, Object> context;

    @NotEmpty
    @Valid // Enable validation of nested items
    @Schema(description = "List of items included in the transaction.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TransactionItemCreateRequestDTO> items;
}
