package com.hsoft.model.dto.v1.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

// Reusable Summary for Providers (e.g., in Transaction Details)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of a provider.")
public class ProviderSummaryResponseDTO {
    @Schema(description = "Unique identifier of the provider.", example = "d4e5f6a7-b8c9-0123-4567-890abcdef012")
    private UUID providerId;

    @Schema(description = "Name of the provider.", example = "Proveedor General S.A.")
    private String name;

    @Schema(description = "Provider NIT (Tax ID).", example = "9876543-C")
    private String nit;
}