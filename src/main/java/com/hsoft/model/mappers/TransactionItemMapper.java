package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.transactions.TransactionItemCreateRequestDTO;
import com.hsoft.model.dto.v1.transactions.TransactionItemResponseDTO;
import com.hsoft.model.entities.v1.TransactionItem;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

// Uses ProductMapper to map the nested ProductSummaryResponseDTO
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface TransactionItemMapper {

    // --- Entity to DTO ---

    // MapStruct will automatically use ProductMapper.productToProductSummaryResponseDTO
    @Mapping(target = "product", source = "product")
    TransactionItemResponseDTO transactionItemToTransactionItemResponseDTO(TransactionItem item);

    List<TransactionItemResponseDTO> transactionItemsToTransactionItemResponseDTOs(List<TransactionItem> items);


    // --- DTO to Entity ---

    // Note: Product and Transaction relationships must be set by the service layer AFTER mapping
    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "product", ignore = true)    // Service finds/sets entity
    @Mapping(target = "transaction", ignore = true) // Service sets parent entity
    @Mapping(target = "subtotal", ignore = true)  // Calculated in entity/service
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    TransactionItem transactionItemCreateRequestDTOToTransactionItem(TransactionItemCreateRequestDTO dto);

    // No update method needed typically, items are usually replaced or transaction cancelled
}
