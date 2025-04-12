package com.hsoft.model.entities.v1;

import com.hsoft.model.types.v1.TransactionStatus;
import com.hsoft.model.types.v1.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * Records sales and purchase transactions for each main user account.
 * Maps to the 'transactions' table in the 'pilot_v2' schema.
 */
@NamedEntityGraph(
        name = "Transaction.detail",
        attributeNodes = {
                @NamedAttributeNode("user"), // Or maybe not needed if context has it
                @NamedAttributeNode("client"),
                @NamedAttributeNode("provider"),
                @NamedAttributeNode("createdByUser"),
                @NamedAttributeNode(value = "items", subgraph = "items-subgraph") // Load items and their product
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "items-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("product") // Load product within each item
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions", schema = "pilot_v2")
@Schema(description = "Represents a sale or purchase transaction linked to a main user account.")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the transaction.", example = "e5f6a7b8-c9d0-1234-5678-90abcdef0123")
    private UUID transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Link to the main owning user account
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The main user account that owns this transaction record.")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    @Schema(description = "Type of transaction.", example = "SALE")
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id") // Nullable, only set for SALE
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The client involved in the transaction (only for SALE type).")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id") // Nullable, only set for PURCHASE
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The provider involved in the transaction (only for PURCHASE type).")
    private Provider provider;

    @Column(name = "transaction_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Date and time when the transaction occurred.", example = "2023-10-27T10:30:00Z")
    private OffsetDateTime transactionDate;

    @Column(name = "reference_number", length = 100)
    @Schema(description = "Reference number (e.g., Invoice No., Receipt No.).", example = "INV-2023-00123")
    private String referenceNumber;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    @Schema(description = "Total monetary value of the transaction.", example = "199.95")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Additional notes related to the transaction.")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    @Schema(description = "Current status of the transaction.", example = "COMPLETED")
    private TransactionStatus status = TransactionStatus.COMPLETED;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the transaction record was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the transaction record was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default
    @Schema(description = "Flexible JSON object for storing custom transaction-specific data.", example = "{\"discountCode\":\"FALL23\", \"shippingMethod\":\"Express\"}")
    private Map<String, Object> context = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id") // Link to the user (main or sub) who actually created it
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The specific user (main or sub-account) who created this transaction record.")
    private User createdByUser;

    // --- Relationship to Items ---
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Schema(description = "List of items included in this transaction.")
    private List<TransactionItem> items = new ArrayList<>();

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.transactionId == null) {
            this.transactionId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
        if (this.transactionDate == null) {
            this.transactionDate = this.createdAt; // Default transaction date if not specified
        }
        // 'updated_at' handled by DB trigger
        // Application logic should enforce client/provider based on type before persisting
        validateClientProvider();
        // Recalculate total based on items before first persist if needed
        // calculateTotalAmount();
    }

    @PreUpdate
    protected void onUpdate() {
        validateClientProvider();
        // Recalculate total based on items before update if needed
        // calculateTotalAmount();
    }

    // --- Helper methods ---
    public void addItem(TransactionItem item) {
        items.add(item);
        item.setTransaction(this);
        // Consider recalculating total amount here or leave to service layer/callback
        // calculateTotalAmount();
    }

    public void removeItem(TransactionItem item) {
        items.remove(item);
        item.setTransaction(null);
        // Consider recalculating total amount here or leave to service layer/callback
        // calculateTotalAmount();
    }

    // Optional: Helper to calculate total amount from items
    public void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(TransactionItem::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Optional: Validation matching DB constraint (better done in service layer)
    private void validateClientProvider() {
        if (this.transactionType == TransactionType.SALE && this.client == null) {
            // throw new IllegalStateException("Client must be set for SALE transactions.");
        }
        if (this.transactionType == TransactionType.PURCHASE && this.provider == null) {
            // throw new IllegalStateException("Provider must be set for PURCHASE transactions.");
        }
        if (this.transactionType == TransactionType.SALE && this.provider != null) {
            // this.provider = null; // Or throw error
        }
        if (this.transactionType == TransactionType.PURCHASE && this.client != null) {
            // this.client = null; // Or throw error
        }
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId != null && Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return transactionId != null ? transactionId.hashCode() : super.hashCode();
    }
}
