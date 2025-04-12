package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for a main user to create a sub-user account.")
public class SubUserCreateRequestDTO {
    @NotBlank @Size(min = 3, max = 50)
    @Schema(description = "Desired username for the sub-user.", example = "subuser_jane", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank @Email @Size(max = 255)
    @Schema(description = "Sub-user's email address.", example = "jane.sub@company.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank @Size(min = 8, max = 100)
    @Schema(description = "Sub-user's initial password.", example = "subUserPassword", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password; // Consider auto-generation and forced reset

    @Schema(description = "Optional custom data for the sub-user.", example = "{\"role\": \"Sales Rep\"}")
    private Map<String, Object> context;
}
