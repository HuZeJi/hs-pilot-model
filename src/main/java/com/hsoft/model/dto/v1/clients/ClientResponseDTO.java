package com.hsoft.model.dto.v1.clients;

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
@Schema(description = "Detailed representation of a client.")
public class ClientResponseDTO {
    @Schema(description = "Unique identifier of the client.", example = "c3d4e5f6-a7b8-9012-3456-7890abcdef01", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID clientId;

    @Schema(description = "ID of the main user account owning this client.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Name of the client.", example = "Important Client Inc.")
    private String name;

    @Schema(description = "Client Tax ID (NIT).", example = "8765432-1")
    private String nit;

    @Schema(description = "Client email address.", example = "contact@client.com")
    private String email;

    @Schema(description = "Client phone number.", example = "555-1111")
    private String phone;

    @Schema(description = "Client address.")
    private String address;

    @Schema(description = "Active status.", example = "true")
    private boolean isActive;

    @Schema(description = "Timestamp when created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Schema(description = "Custom data.", example = "{\"account_manager\": \"user_uuid\", \"tier\": \"gold\"}")
    private Map<String, Object> context;
}
