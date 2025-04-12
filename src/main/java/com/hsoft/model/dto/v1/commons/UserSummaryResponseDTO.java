package com.hsoft.model.dto.v1.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// Reusable Summary for Users (e.g., createdBy)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary representation of a user.")
public class UserSummaryResponseDTO {
    @Schema(description = "Unique identifier of the user.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID userId;

    @Schema(description = "Username of the user.", example = "johndoe")
    private String username;
}
