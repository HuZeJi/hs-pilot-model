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
 * Stores customer information for each main user account.
 * Maps to the 'clients' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients", schema = "pilot_v2")
@Schema(description = "Represents a client (customer) of a main user account.")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the client.", example = "c3d4e5f6-a7b8-9012-3456-7890abcdef01")
    private UUID clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Link to the main owning user
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The main user account this client belongs to.")
    private User user;

    @Column(nullable = false)
    @Schema(description = "Name of the client.", example = "Cliente Importante S.A.")
    private String name;

    @Column(length = 20)
    @Schema(description = "Client Tax ID (NIT - Guatemala). Use 'C/F' for Consumidor Final.", example = "7654321-K")
    private String nit;

    @Column(length = 255)
    @Schema(description = "Client email address.", example = "contacto@clienteimportante.com")
    private String email;

    @Column(length = 50)
    @Schema(description = "Client phone number.", example = "+502 5555 1212")
    private String phone;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Client address.", example = "456 Avenida Reforma, Zona 10, Guatemala")
    private String address;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Flag indicating if the client record is active.", example = "true")
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the client was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the client was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default
    @Schema(description = "Flexible JSON object for storing custom client-specific data.", example = "{\"creditLimit\": 5000, \"segment\":\"VIP\"}")
    private Map<String, Object> context = new HashMap<>();

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.clientId == null) {
            this.clientId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId != null && Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return clientId != null ? clientId.hashCode() : super.hashCode();
    }
}
