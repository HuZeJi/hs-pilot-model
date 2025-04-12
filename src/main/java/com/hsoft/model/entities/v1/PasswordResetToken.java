package com.hsoft.model.entities.v1;

import jakarta.persistence.*;
import lombok.*; // Using individual annotations for clarity

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a password reset token associated with a user account.
 * Maps to the 'password_reset_tokens' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor // JPA requires a no-arg constructor
@Entity
@Table(name = "password_reset_tokens", schema = "pilot_v2", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token", name = "idx_prt_token") // Ensure token uniqueness at DB level
})
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id", updatable = false, nullable = false)
    private UUID tokenId;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch is usually sufficient
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude // Avoid recursion in toString
    @EqualsAndHashCode.Exclude // Exclude relational fields from default equals/hashCode
    private User user;

    @Column(name = "expiry_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expiryDate;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    /**
     * Constructor for creating a new PasswordResetToken.
     *
     * @param token      The unique token string.
     * @param user       The user associated with the token.
     * @param expiryDate The token's expiration date and time.
     */
    public PasswordResetToken(String token, User user, OffsetDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        // tokenId and createdAt will be handled by JPA/DB
    }

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.tokenId == null) {
            this.tokenId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        // If the ID is null (transient entity), objects are equal only if they are the same instance.
        // If the ID is not null, compare by ID. Handles proxy objects correctly.
        return tokenId != null && Objects.equals(tokenId, that.tokenId);
    }

    @Override
    public int hashCode() {
        // Use ID for hashcode if available, otherwise rely on Object's hashCode
        return tokenId != null ? tokenId.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "tokenId=" + tokenId +
                ", token='*****'" + // Mask token in logs
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
