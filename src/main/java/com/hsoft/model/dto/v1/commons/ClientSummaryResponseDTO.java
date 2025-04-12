package com.hsoft.model.dto.v1.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// Reusable Summary for Clients (e.g., in Transaction Details)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of a client.")
public class ClientSummaryResponseDTO {
    @Schema(description = "Unique identifier of the client.", example = "c3d4e5f6-a7b8-9012-3456-7890abcdef01")
    private UUID clientId;

    @Schema(description = "Name of the client.", example = "Cliente Importante S.A.")
    private String name;

    @Schema(description = "Client NIT (Tax ID).", example = "7654321-K")
    private String nit;
}
