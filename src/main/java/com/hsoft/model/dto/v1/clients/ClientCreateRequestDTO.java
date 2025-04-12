package com.hsoft.model.dto.v1.clients;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Request body for creating a new client.")
public class ClientCreateRequestDTO {
    @NotBlank @Size(max = 255)
    @Schema(description = "Name of the client.", example = "Important Client Inc.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 20)
    @Schema(description = "Client Tax ID (NIT - Guatemala). Use 'C/F' for Consumidor Final.", example = "8765432-1")
    private String nit;

    @Email @Size(max = 255)
    @Schema(description = "Client email address.", example = "contact@client.com")
    private String email;

    @Size(max = 50)
    @Schema(description = "Client phone number.", example = "555-1111")
    private String phone;

    @Schema(description = "Client address.")
    private String address;

    @Schema(description = "Initial active status.", example = "true")
    private Boolean isActive = true;

    @Schema(description = "Custom data for the client.", example = "{\"account_manager\": \"user_uuid\", \"tier\": \"gold\"}")
    private Map<String, Object> context;
}
