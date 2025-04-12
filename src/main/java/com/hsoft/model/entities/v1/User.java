package com.hsoft.model.entities.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode; // For Hibernate 6+
import org.hibernate.type.SqlTypes;          // For Hibernate 6+
// import io.hypersistence.utils.hibernate.type.json.JsonType; // Alt: Hypersistence Utils
// import jakarta.persistence.Convert; // Alt: For custom AttributeConverter

import java.time.OffsetDateTime;
import java.util.*;

/**
 * Stores user accounts. Can be main accounts (parentUser is NULL)
 * or sub-accounts linked to a main account.
 * Maps to the 'users' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "pilot_v2", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "users_username_key"),
        @UniqueConstraint(columnNames = "email", name = "users_email_key"),
        @UniqueConstraint(columnNames = "company_nit", name = "users_company_nit_key")
})
@Schema(description = "Represents a user account, which can be a main company account or a sub-account.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Use UUID generation
    @Column(name = "user_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the user.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_user_id") // Nullable FK for parent relationship
    @ToString.Exclude // Avoid recursion
    @EqualsAndHashCode.Exclude // Avoid recursion
    @Schema(description = "The main user account this user belongs to, if this is a sub-account. Null for main accounts.")
    private User parentUser; // Self-referencing: Link to parent

    @Column(nullable = false, length = 50)
    @Schema(description = "Unique username for login.", example = "johndoe")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Unique email address for the user.", example = "john.doe@company.com")
    private String email;

    @Column(name = "password_hash", nullable = false)
    @Schema(description = "Hashed password for the user (write-only in practice).")
    @ToString.Exclude // Don't include password hash in toString
    private String passwordHash;

    @Column(name = "company_name", nullable = false)
    @Schema(description = "Name of the company associated with the main account.", example = "Acme Corporation")
    private String companyName;

    @Column(name = "company_nit", length = 20)
    @Schema(description = "Company Tax ID (NIT - Guatemala). Should be unique for main accounts.", example = "1234567-8")
    private String companyNit;

    @Column(name = "company_address", columnDefinition = "TEXT")
    @Schema(description = "Physical address of the company.", example = "123 Main St, Guatemala City")
    private String companyAddress;

    @Column(name = "company_phone", length = 50)
    @Schema(description = "Contact phone number for the company.", example = "+502 2345 6789")
    private String companyPhone;

    @Column(name = "is_active", nullable = false)
    @Builder.Default // Set default for builder
    @Schema(description = "Flag indicating if the user account is active.", example = "true")
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the user was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the user was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON) // Hibernate 6+ native JSON mapping
    // @Convert(converter = JsonMapConverter.class) // Alt: Custom JPA converter
    // @Type(JsonType.class) // Alt: Hypersistence Utils
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default // Initialize for builder
    @Schema(description = "Flexible JSON object for storing custom user-specific data.", example = "{\"department\":\"Sales\", \"employeeId\":\"E123\"}")
    private Map<String, Object> context = new HashMap<>();

    // --- Relationships ---

    @OneToMany(mappedBy = "parentUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude // Avoid recursion
    @EqualsAndHashCode.Exclude // Avoid recursion
    @Builder.Default
    @Schema(description = "List of sub-accounts belonging to this main account.")
    private List<User> subAccounts = new ArrayList<>(); // Self-referencing: Link to children

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Schema(description = "Products associated with this main user account.")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Schema(description = "Clients associated with this main user account.")
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Schema(description = "Providers associated with this main user account.")
    private List<Provider> providers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Schema(description = "Transactions associated with this main user account.")
    private List<Transaction> transactions = new ArrayList<>();

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.userId == null) { // Ensure ID is set if not already (e.g., if assigned manually before persist)
            this.userId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
        // 'updated_at' handled by DB trigger
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // If the ID is null (transient entity), objects are equal only if they are the same instance.
        // If the ID is not null, compare by ID. Handles proxy objects correctly.
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        // Use ID for hashcode if available, otherwise rely on Object's hashCode
        return userId != null ? userId.hashCode() : super.hashCode();
    }
}
