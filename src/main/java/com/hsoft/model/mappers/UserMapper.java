package com.hsoft.model.mappers;

import com.hsoft.model.dto.v1.commons.UserSummaryResponseDTO;
import com.hsoft.model.dto.v1.users.CompanyInfoUpdateRequestDTO;
import com.hsoft.model.dto.v1.users.RegisterRequestDTO;
import com.hsoft.model.dto.v1.users.SubUserCreateRequestDTO;
import com.hsoft.model.dto.v1.users.UserResponseDTO;
import com.hsoft.model.dto.v1.users.UserUpdateRequestDTO;
import com.hsoft.model.entities.v1.User;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Useful for PATCH updates
public interface UserMapper {

    // --- Entity to DTO ---

    @Mapping(target = "parentUserId", source = "parentUser.userId")
    UserResponseDTO userToUserResponseDTO(User user);

    UserSummaryResponseDTO userToUserSummaryResponseDTO(User user);

    List<UserResponseDTO> usersToUserResponseDTOs(List<User> users);
    List<UserSummaryResponseDTO> usersToUserSummaryResponseDTOs(List<User> users);


    // --- DTO to Entity ---

    // For Registration (Main User)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // Set by service
    @Mapping(target = "parentUser", ignore = true) // Explicitly null for main user, set by service conceptually
    @Mapping(target = "subAccounts", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "providers", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true) // Service should set default
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    User registerRequestDTOToUser(RegisterRequestDTO dto);

    // For Sub-User Creation
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // Set by service
    @Mapping(target = "parentUser", ignore = true) // Set by service
    @Mapping(target = "subAccounts", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "providers", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true) // Service should set default
    @Mapping(target = "companyName", ignore = true) // Usually inherited or N/A for sub-user entity field
    @Mapping(target = "companyNit", ignore = true)
    @Mapping(target = "companyAddress", ignore = true)
    @Mapping(target = "companyPhone", ignore = true)
    @Mapping(target = "context", expression = "java(dto.getContext() != null ? dto.getContext() : new java.util.HashMap<>())")
    User subUserCreateRequestDTOToUser(SubUserCreateRequestDTO dto);

    // --- Update Entity from DTO (@MappingTarget) ---

    // For updating User profile (self or sub-user)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "parentUser", ignore = true)
    @Mapping(target = "subAccounts", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "providers", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "companyName", ignore = true) // Usually not updated here
    @Mapping(target = "companyNit", ignore = true)
    @Mapping(target = "companyAddress", ignore = true)
    @Mapping(target = "companyPhone", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDTO dto, @MappingTarget User user);

    // For updating Company Info by Main User
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "parentUser", ignore = true)
    @Mapping(target = "subAccounts", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "providers", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompanyInfoFromDto(CompanyInfoUpdateRequestDTO dto, @MappingTarget User user);
}
