package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed representation of a user account.")
public class UserResponseDTO {
    @Schema(description = "Unique identifier of the user.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Username of the user.", example = "johndoe")
    private String username;

    @Schema(description = "Email address of the user.", example = "john.doe@company.com")
    private String email;

    @Schema(description = "Flag indicating if the user account is active.", example = "true")
    private boolean isActive;

    @Schema(description = "Timestamp when the user was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Schema(description = "Custom data associated with the user.", example = "{\"department\":\"Sales\", \"employeeId\":\"E123\"}")
    private Map<String, Object> context;

    // Fields relevant if this user is a sub-user
    @Schema(description = "ID of the main account this user belongs to (null for main accounts).", example = "f0e9d8c7-b6a5-4321-fedc-ba9876543210")
    private UUID parentUserId; // ID only might be sufficient

    // Fields relevant if this user is a main account
    @Schema(description = "Company name associated with the main account.", example = "Acme Corporation")
    private String companyName;

    @Schema(description = "Company Tax ID (NIT - Guatemala).", example = "1234567-8")
    private String companyNit;

    @Schema(description = "Company address.", example = "123 Main St, Guatemala City")
    private String companyAddress;

    @Schema(description = "Company phone number.", example = "+502 2345 6789")
    private String companyPhone;

    // Optionally include summary of sub-accounts if it's a main user view
    // @Schema(description = "List of sub-accounts belonging to this main account (summary view).")
    // private List<UserSummaryResponse> subAccounts;
}