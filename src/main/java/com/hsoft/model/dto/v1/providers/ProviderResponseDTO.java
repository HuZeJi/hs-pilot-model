package com.hsoft.model.dto.v1.providers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed representation of a provider.")
public class ProviderResponseDTO {
    @Schema(description = "Unique identifier of the provider.", example = "d4e5f6a7-b8c9-0123-4567-890abcdef012", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID providerId;

    @Schema(description = "ID of the main user account owning this provider.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Name of the provider.", example = "Main Supplier Ltd.")
    private String name;

    @Schema(description = "Provider Tax ID (NIT).", example = "1122334-5")
    private String nit;

    @Schema(description = "Provider email address.", example = "sales@supplier.com")
    private String email;

    @Schema(description = "Provider phone number.", example = "555-3333")
    private String phone;

    @Schema(description = "Provider address.")
    private String address;

    @Schema(description = "Primary contact person.", example = "John Smith")
    private String contactPerson;

    @Schema(description = "Active status.", example = "true")
    private boolean isActive;

    @Schema(description = "Timestamp when created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Schema(description = "Custom data.", example = "{\"payment_terms\": \"NET30\", \"rating\": 5}")
    private Map<String, Object> context;
}
