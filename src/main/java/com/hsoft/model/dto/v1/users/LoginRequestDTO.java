package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for user login.")
public class LoginRequestDTO {
    @NotBlank
    @Schema(description = "Username or email address.", example = "main@company.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String usernameOrEmail;

    @NotBlank
    @Schema(description = "User's password.", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
