package com.hsoft.model.dto.v1.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Reusable DTO for simple status updates
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update the active status of an entity.")
public class StatusUpdateRequestDTO {
    @NotNull
    @Schema(description = "The desired active status.", example = "true")
    private Boolean isActive;
}
