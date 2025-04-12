package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.commons.ProviderSummaryResponseDTO;
import com.hsoft.model.dto.v1.providers.ProviderCreateRequestDTO;
import com.hsoft.model.dto.v1.providers.ProviderResponseDTO;
import com.hsoft.model.dto.v1.providers.ProviderUpdateRequestDTO;
import com.hsoft.model.entities.v1.Provider;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProviderMapper {

    // --- Entity to DTO ---

    @Mapping(target = "userId", source = "user.userId")
    ProviderResponseDTO providerToProviderResponseDTO(Provider provider);

    ProviderSummaryResponseDTO providerToProviderSummaryResponseDTO(Provider provider);

    List<ProviderResponseDTO> providersToProviderResponseDTOs(List<Provider> providers);

    // --- DTO to Entity ---

    @Mapping(target = "providerId", ignore = true)
    @Mapping(target = "user", ignore = true) // Set by service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", expression = "java(dto.getIsActive() != null ? dto.getIsActive() : true)")
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    Provider providerCreateRequestDTOToProvider(ProviderCreateRequestDTO dto);

    // --- Update Entity from DTO ---

    @Mapping(target = "providerId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProviderFromDto(ProviderUpdateRequestDTO dto, @MappingTarget Provider provider);
}
