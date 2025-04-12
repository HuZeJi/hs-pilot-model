package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body to confirm password reset using a token.")
public class ConfirmPasswordResetRequestDTO {
    @NotBlank
    @Schema(description = "The password reset token received via email.", example = "aValidResetTokenFromEmail", requiredMode = Schema.RequiredMode.REQUIRED)
    private String resetToken;

    @NotBlank @Size(min = 8, max = 100)
    @Schema(description = "The desired new password.", example = "newStrongerPassword456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
