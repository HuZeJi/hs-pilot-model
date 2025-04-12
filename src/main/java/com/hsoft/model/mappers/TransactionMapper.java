package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.transactions.TransactionCreateRequestDTO;
import com.hsoft.model.dto.v1.transactions.TransactionDetailResponseDTO;
import com.hsoft.model.dto.v1.transactions.TransactionSummaryResponseDTO;
import com.hsoft.model.dto.v1.transactions.TransactionUpdateRequestDTO;
import com.hsoft.model.entities.v1.Transaction;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

// Uses other mappers for nested DTOs
@Mapper(componentModel = "spring",
        uses = {UserMapper.class, ClientMapper.class, ProviderMapper.class, TransactionItemMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    // --- Entity to DTO ---

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "client", source = "client") // Uses ClientMapper.clientToClientSummaryResponseDTO
    @Mapping(target = "provider", source = "provider") // Uses ProviderMapper.providerToProviderSummaryResponseDTO
    @Mapping(target = "createdByUser", source = "createdByUser") // Uses UserMapper.userToUserSummaryResponseDTO
    @Mapping(target = "items", source = "items") // Uses TransactionItemMapper.transactionItemsToTransactionItemResponseDTOs
    TransactionDetailResponseDTO transactionToTransactionDetailResponseDTO(Transaction transaction);

    @Mapping(target = "clientId", source = "client.clientId")
    @Mapping(target = "clientName", source = "client.name") // Assumes client is fetched with transaction for list view
    @Mapping(target = "providerId", source = "provider.providerId")
    @Mapping(target = "providerName", source = "provider.name") // Assumes provider is fetched
    @Mapping(target = "createdByUser", source = "createdByUser") // Uses UserMapper.userToUserSummaryResponseDTO
    TransactionSummaryResponseDTO transactionToTransactionSummaryResponseDTO(Transaction transaction);

    List<TransactionSummaryResponseDTO> transactionsToTransactionSummaryResponseDTOs(List<Transaction> transactions);


    // --- DTO to Entity ---

    // Note: Relationships (user, creator, client/provider, items) and calculations (total) must be set by service
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "transactionType", ignore = true) // Set by service based on endpoint
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus() : com.hsoft.model.types.v1.TransactionStatus.COMPLETED)") // Default status
    @Mapping(target = "transactionDate", expression = "java(dto.getTransactionDate() != null ? dto.getTransactionDate() : java.time.OffsetDateTime.now())") // Default date
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    Transaction transactionCreateRequestDTOToTransaction(TransactionCreateRequestDTO dto);


    // --- Update Entity from DTO ---

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "transactionType", ignore = true)
    @Mapping(target = "transactionDate", ignore = true) // Typically not updated
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTransactionFromDto(TransactionUpdateRequestDTO dto, @MappingTarget Transaction transaction);
}
