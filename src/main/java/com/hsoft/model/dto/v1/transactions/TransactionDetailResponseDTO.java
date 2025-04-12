package com.hsoft.model.dto.v1.transactions;

import com.hsoft.model.dto.v1.commons.ClientSummaryResponseDTO;
import com.hsoft.model.dto.v1.commons.ProviderSummaryResponseDTO;
import com.hsoft.model.dto.v1.commons.UserSummaryResponseDTO;
import com.hsoft.model.types.v1.TransactionStatus;
import com.hsoft.model.types.v1.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed representation of a transaction, including items.")
public class TransactionDetailResponseDTO {
    @Schema(description = "Unique identifier of the transaction.", example = "e5f6a7b8-c9d0-1234-5678-90abcdef0123", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID transactionId;

    @Schema(description = "ID of the main user account owning this transaction.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Type of transaction.", example = "SALE")
    private TransactionType transactionType;

    // Include summaries for client/provider
    @Schema(description = "Summary of the client involved (if SALE).")
    private ClientSummaryResponseDTO client;

    @Schema(description = "Summary of the provider involved (if PURCHASE).")
    private ProviderSummaryResponseDTO provider;

    @Schema(description = "Date and time of the transaction.", example = "2023-11-15T14:30:00Z")
    private OffsetDateTime transactionDate;

    @Schema(description = "Reference number.", example = "INV-001")
    private String referenceNumber;

    @Schema(description = "Total monetary value of the transaction.", example = "199.95", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal totalAmount;

    @Schema(description = "Additional notes.")
    private String notes;

    @Schema(description = "Status of the transaction.", example = "COMPLETED")
    private TransactionStatus status;

    @Schema(description = "Timestamp when created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Schema(description = "Custom data associated with the transaction.", example = "{\"sales_channel\": \"web\"}")
    private Map<String, Object> context;

    @Schema(description = "Summary of the user who created the transaction record.")
    private UserSummaryResponseDTO createdByUser; // Summary is likely sufficient

    @Schema(description = "List of items included in the transaction.")
    private List<TransactionItemResponseDTO> items;
}
