package com.hsoft.model.dto.v1.clients;

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
@Schema(description = "Request body for updating an existing client.")
public class ClientUpdateRequestDTO {
    // Include only fields allowed to be updated
    @Size(max = 255)
    @Schema(description = "Updated client name.")
    private String name;

    @Size(max = 20)
    @Schema(description = "Updated Client Tax ID (NIT).")
    private String nit;

    @Email @Size(max = 255)
    @Schema(description = "Updated email address.")
    private String email;

    @Size(max = 50)
    @Schema(description = "Updated phone number.", example = "555-2222")
    private String phone;

    @Schema(description = "Updated address.")
    private String address;

    @Schema(description = "Updated custom data.", example = "{\"tier\": \"platinum\"}")
    private Map<String, Object> context;
}
