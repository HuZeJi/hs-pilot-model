package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.commons.ProductSummaryResponseDTO;
import com.hsoft.model.dto.v1.products.ProductCreateRequestDTO;
import com.hsoft.model.dto.v1.products.ProductResponseDTO;
import com.hsoft.model.dto.v1.products.ProductUpdateRequestDTO;
import com.hsoft.model.entities.v1.Product;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    // --- Entity to DTO ---

    @Mapping(target = "userId", source = "user.userId")
    ProductResponseDTO productToProductResponseDTO(Product product);

    ProductSummaryResponseDTO productToProductSummaryResponseDTO(Product product);

    List<ProductResponseDTO> productsToProductResponseDTOs(List<Product> products);
    List<ProductSummaryResponseDTO> productsToProductSummaryResponseDTOs(List<Product> products);

    // --- DTO to Entity ---

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "user", ignore = true) // Set by service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", expression = "java(dto.getIsActive() != null ? dto.getIsActive() : true)") // Default active
    @Mapping(target = "currentStock", expression = "java(dto.getCurrentStock() != null ? dto.getCurrentStock() : 0)") // Default stock
    @Mapping(target = "unitOfMeasure", expression = "java(dto.getUnitOfMeasure() != null ? dto.getUnitOfMeasure() : \"unidad\")") // Default unit
    @Mapping(target = "purchasePrice", expression = "java(dto.getPurchasePrice() != null ? dto.getPurchasePrice() : java.math.BigDecimal.ZERO)") // Default price
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    Product productCreateRequestDTOToProduct(ProductCreateRequestDTO dto);

    // --- Update Entity from DTO ---

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true) // Usually updated via specific endpoint
    @Mapping(target = "currentStock", ignore = true) // Usually updated via transactions/adjustments
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductUpdateRequestDTO dto, @MappingTarget Product product);
}
