package com.hsoft.model.dto.v1.providers;

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
@Schema(description = "Request body for creating a new provider.")
public class ProviderCreateRequestDTO {
    @NotBlank @Size(max = 255)
    @Schema(description = "Name of the provider.", example = "Main Supplier Ltd.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 20)
    @Schema(description = "Provider Tax ID (NIT - Guatemala).", example = "1122334-5")
    private String nit;

    @Email @Size(max = 255)
    @Schema(description = "Provider email address.", example = "sales@supplier.com")
    private String email;

    @Size(max = 50)
    @Schema(description = "Provider phone number.", example = "555-3333")
    private String phone;

    @Schema(description = "Provider address.")
    private String address;

    @Size(max = 150)
    @Schema(description = "Primary contact person.", example = "John Smith")
    private String contactPerson;

    @Schema(description = "Initial active status.", example = "true")
    private Boolean isActive = true;

    @Schema(description = "Custom data for the provider.", example = "{\"payment_terms\": \"NET30\", \"rating\": 5}")
    private Map<String, Object> context;
}
