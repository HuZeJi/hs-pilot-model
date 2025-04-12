package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating a user's profile information (self or sub-user by main).")
public class UserUpdateRequestDTO {
    // Include only fields allowed to be updated
    @Email @Size(max = 255)
    @Schema(description = "New email address.", example = "new.email@company.com")
    private String email; // Example: email might require verification

    @Size(max = 50)
    @Schema(description = "New username (if allowed).", example = "john.doe.updated")
    private String username; // Changing username might have implications

    @Schema(description = "Updated custom data.", example = "{\"preferred_theme\": \"dark\"}")
    private Map<String, Object> context;

    // Add other updatable fields as needed (e.g., for sub-user updates by main user)
}
