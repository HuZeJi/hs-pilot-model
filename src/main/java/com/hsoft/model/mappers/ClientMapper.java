package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.clients.ClientCreateRequestDTO;
import com.hsoft.model.dto.v1.clients.ClientResponseDTO;
import com.hsoft.model.dto.v1.clients.ClientUpdateRequestDTO;
import com.hsoft.model.dto.v1.commons.ClientSummaryResponseDTO;
import com.hsoft.model.entities.v1.Client;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    // --- Entity to DTO ---

    @Mapping(target = "userId", source = "user.userId")
    ClientResponseDTO clientToClientResponseDTO(Client client);

    ClientSummaryResponseDTO clientToClientSummaryResponseDTO(Client client);

    List<ClientResponseDTO> clientsToClientResponseDTOs(List<Client> clients);

    // --- DTO to Entity ---

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "user", ignore = true) // Set by service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", expression = "java(dto.getIsActive() != null ? dto.getIsActive() : true)")
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    Client clientCreateRequestDTOToClient(ClientCreateRequestDTO dto);

    // --- Update Entity from DTO ---

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDto(ClientUpdateRequestDTO dto, @MappingTarget Client client);
}
