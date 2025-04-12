package com.hsoft.model.entities.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Line items for each transaction, linking products with quantities and prices.
 * Maps to the 'transaction_items' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_items", schema = "pilot_v2", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"transaction_id", "product_id"}, name = "uq_transaction_product")
})
@Schema(description = "Represents a single line item within a transaction.")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the transaction item.", example = "f6a7b8c9-d0e1-2345-6789-0abcdef01234")
    private UUID itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false) // Link back to parent transaction
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The transaction this item belongs to.")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY) // Eager might be acceptable if product name/details often needed with item
    @JoinColumn(name = "product_id", nullable = false) // Link to the specific product
    @ToString.Exclude // Avoid potential large object stringification
    @EqualsAndHashCode.Exclude
    @Schema(description = "The product associated with this line item.")
    private Product product;

    @Column(nullable = false)
    @Schema(description = "Quantity of the product for this line item.", example = "2")
    private int quantity; // DB Check constraint (quantity != 0)

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Price per unit of the product at the time of the transaction.", example = "29.99")
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "Subtotal for this line item (quantity * unitPrice).", example = "59.98", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal subtotal; // Automatically calculated

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default
    @Schema(description = "Flexible JSON object for storing custom item-specific data.", example = "{\"discountApplied\":\"10%\", \"serialNumber\":\"SN12345\"}")
    private Map<String, Object> context = new HashMap<>();


    // --- Lifecycle Callbacks / Setters for Calculation ---
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (this.unitPrice != null) {
            // Use BigDecimal for quantity calculation to avoid precision issues if needed,
            // but multiplying by int is usually fine here.
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        } else {
            this.subtotal = BigDecimal.ZERO; // Or handle as an error/invalid state
        }
        // Note: Triggering recalculation of Transaction.totalAmount should ideally
        // happen in the service layer or via JPA listeners on the Transaction entity
        // when its item collection changes. Directly modifying parent from child callback
        // can be complex and might lead to issues.
    }

    // Custom setters to ensure subtotal is recalculated on change
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateSubtotal(); // Recalculate when quantity changes
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        calculateSubtotal(); // Recalculate when unit price changes
    }

    // --- Custom equals/hashCode based on ID ---
    // Note: For items within a collection owned by a parent (Transaction),
    // relying on the ID is standard. If items needed unique identity based
    // on Transaction+Product before being saved, a different approach would be needed.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionItem that = (TransactionItem) o;
        return itemId != null && Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return itemId != null ? itemId.hashCode() : super.hashCode();
    }
}
