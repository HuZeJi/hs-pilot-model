package com.hsoft.model.dto.v1.providers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Request body for updating an existing provider.")
public class ProviderUpdateRequestDTO {
    // Include only fields allowed to be updated
    @Size(max = 255)
    @Schema(description = "Updated provider name.")
    private String name;

    @Size(max = 20)
    @Schema(description = "Updated Provider Tax ID (NIT).")
    private String nit;

    @Email @Size(max = 255)
    @Schema(description = "Updated email address.")
    private String email;

    @Size(max = 50)
    @Schema(description = "Updated phone number.")
    private String phone;

    @Schema(description = "Updated address.")
    private String address;

    @Size(max = 150)
    @Schema(description = "Updated contact person.", example = "Jane Doe")
    private String contactPerson;

    @Schema(description = "Updated custom data.", example = "{\"payment_terms\": \"NET45\"}")
    private Map<String, Object> context;
}
