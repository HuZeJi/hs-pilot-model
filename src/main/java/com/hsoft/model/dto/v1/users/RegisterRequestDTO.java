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
@Schema(description = "Request body for registering a new main user account.")
public class RegisterRequestDTO {
    @NotBlank @Size(min = 3, max = 50)
    @Schema(description = "Desired username.", example = "newmainuser", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank @Email @Size(max = 255)
    @Schema(description = "User's email address.", example = "main@company.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank @Size(min = 8, max = 100) // Adjust size constraints as needed
    @Schema(description = "User's desired password.", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank @Size(max = 255)
    @Schema(description = "Name of the company.", example = "My New Company LLC", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;

    @Size(max = 20)
    @Schema(description = "Company Tax ID (NIT - Guatemala).", example = "1234567-9")
    private String companyNit;

    @Schema(description = "Company address.")
    private String companyAddress;

    @Size(max = 50)
    @Schema(description = "Company phone number.", example = "555-1234")
    private String companyPhone;

    @Schema(description = "Optional custom data for the user.", example = "{\"initial_setup_flag\": true}")
    private Map<String, Object> context;
}
