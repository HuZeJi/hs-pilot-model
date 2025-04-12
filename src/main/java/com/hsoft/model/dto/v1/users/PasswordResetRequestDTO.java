package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body to initiate a password reset.")
public class PasswordResetRequestDTO {
    @NotBlank @Email
    @Schema(description = "Email address of the account to reset password for.", example = "user@company.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
