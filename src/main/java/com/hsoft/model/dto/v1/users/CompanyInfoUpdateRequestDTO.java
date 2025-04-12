package com.hsoft.model.dto.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating main company information.")
public class CompanyInfoUpdateRequestDTO {
    @Size(max = 255)
    @Schema(description = "Updated company name.")
    private String companyName;

    @Size(max = 20)
    @Schema(description = "Updated company Tax ID (NIT).")
    private String companyNit;

    @Schema(description = "Updated company address.")
    private String companyAddress;

    @Size(max = 50)
    @Schema(description = "Updated company phone number.")
    private String companyPhone;

    // Potentially allow updating company-level context here too
    @Schema(description = "Updated company-level custom data.")
    private Map<String, Object> context;
}
