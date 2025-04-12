package com.hsoft.model.entities.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Catalog of products or services offered by a main user account.
 * Maps to the 'products' table in the 'pilot_v2' schema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products", schema = "pilot_v2", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "sku"}, name = "idx_products_user_sku")
})
@Schema(description = "Represents a product or service offered by a main user account.")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the product.", example = "b2c3d4e5-f6a7-8901-2345-67890abcdef0")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Link to the main owning user
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "The main user account that owns this product.")
    private User user;

    @Column(length = 100)
    @Schema(description = "Stock Keeping Unit. Should be unique per main user account.", example = "SKU-WIDGET-RED")
    private String sku;

    @Column(nullable = false)
    @Schema(description = "Name of the product.", example = "Red Widget")
    private String name;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the product.", example = "A high-quality red widget.")
    private String description;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    @Builder.Default
    @Schema(description = "Price paid to the provider for the product.", example = "15.50")
    private BigDecimal purchasePrice = BigDecimal.ZERO;

    @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    @Schema(description = "Price charged to the client for the product.", example = "29.99")
    private BigDecimal salePrice = BigDecimal.ZERO;

    @Column(name = "current_stock", nullable = false)
    @Builder.Default
    @Schema(description = "Current quantity of this product in stock.", example = "100")
    private int currentStock = 0;

    @Column(name = "unit_of_measure", length = 50)
    @Builder.Default
    @Schema(description = "Unit for measuring stock (e.g., unidad, kg, litro, caja).", example = "unidad")
    private String unitOfMeasure = "unidad";

    @Column(length = 100)
    @Schema(description = "Category the product belongs to.", example = "Widgets")
    private String category;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Flag indicating if the product is active/available.", example = "true")
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the product was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Schema(description = "Timestamp when the product was last updated.", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime updatedAt;

    // --- JSONB Context Field ---
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    @Builder.Default
    @Schema(description = "Flexible JSON object for storing custom product-specific data.", example = "{\"color\":\"Red\", \"material\":\"Plastic\"}")
    private Map<String, Object> context = new HashMap<>();

    // --- Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        if (this.productId == null) {
            this.productId = UUID.randomUUID();
        }
        this.createdAt = OffsetDateTime.now();
    }

    // --- Custom equals/hashCode based on ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId != null && Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : super.hashCode();
    }
}
