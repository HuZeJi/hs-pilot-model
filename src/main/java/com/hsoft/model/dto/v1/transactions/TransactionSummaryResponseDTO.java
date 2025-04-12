package com.hsoft.model.dto.v1.transactions;

import com.hsoft.model.dto.v1.commons.UserSummaryResponseDTO;
import com.hsoft.model.types.v1.TransactionStatus;
import com.hsoft.model.types.v1.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Summary representation of a transaction for list views.")
public class TransactionSummaryResponseDTO {
    @Schema(description = "Unique identifier of the transaction.", example = "e5f6a7b8-c9d0-1234-5678-90abcdef0123", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID transactionId;

    @Schema(description = "Type of transaction.", example = "SALE")
    private TransactionType transactionType;

    // Include IDs or simple names for client/provider in summary
    @Schema(description = "Client ID (if SALE).")
    private UUID clientId;
    @Schema(description = "Client Name (if SALE).")
    private String clientName; // Denormalized for convenience, fetch via join

    @Schema(description = "Provider ID (if PURCHASE).")
    private UUID providerId;
    @Schema(description = "Provider Name (if PURCHASE).")
    private String providerName; // Denormalized

    @Schema(description = "Date of the transaction.", example = "2023-11-15T14:30:00Z")
    private OffsetDateTime transactionDate;

    @Schema(description = "Reference number.", example = "INV-001")
    private String referenceNumber;

    @Schema(description = "Total monetary value.", example = "199.95")
    private BigDecimal totalAmount;

    @Schema(description = "Status.", example = "COMPLETED")
    private TransactionStatus status;

    @Schema(description = "Summary of the creator user.")
    private UserSummaryResponseDTO createdByUser;

    @Schema(description = "Timestamp when created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;
}