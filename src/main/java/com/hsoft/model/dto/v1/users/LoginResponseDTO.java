package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response body containing an authentication token.")
public class LoginResponseDTO {
    @Schema(description = "JWT access token for authenticating subsequent requests.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    // Optionally include refresh token, token type, expiry time etc.
}
