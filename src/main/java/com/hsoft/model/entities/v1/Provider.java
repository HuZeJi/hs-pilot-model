package com.hsoft.model.entities.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Stores supplier information for each main user account.
 * Maps to the 'providers' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "providers", schema = "pilot_v2", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "nit"}, name = "idx_providers_user_nit")
})
@Schema(description = "Represents a provider (supplier) for a main user account.")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the provider.", example = "d4e5f6a7-b8c9-0123-4567-890abcdef012")
    private UUID providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Link to the main owning user
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The main user account this provider is associated with.")
    private User user;

    @Column(nullable = false)
    @Schema(description = "Name of the provider.", example = "Proveedor General S.A.")
    private String name;

    @Column(length = 20)
    @Schema(description = "Provider Tax ID (NIT - Guatemala).", example = "9876543-C")
    private String nit;

    @Column(length = 255)
    @Schema(description = "Provider email address.", example = "ventas@proveedorgeneral.com")
    private String email;

    @Column(length = 50)
    @Schema(description = "Provider phone number.", example = "+502 6666 7777")
    private String phone;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Provider address.", example = "Km 15 Carretera al Pacífico, Villa Nueva")
    private String address;

    @Column(name = "contact_person", length = 150)
    @Schema(description = "Name of the primary contact person at the provider.", example = "Ana Lopez")
    private String contactPerson;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Flag indicating if the provider record is active.", example = "true")
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the provider was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the provider was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default
    @Schema(description = "Flexible JSON object for storing custom provider-specific data.", example = "{\"paymentTerms\":\"Net 30\", \"preferredShipper\":\"DHL\"}")
    private Map<String, Object> context = new HashMap<>();

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.providerId == null) {
            this.providerId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId != null && Objects.equals(providerId, provider.providerId);
    }

    @Override
    public int hashCode() {
        return providerId != null ? providerId.hashCode() : super.hashCode();
    }
}
