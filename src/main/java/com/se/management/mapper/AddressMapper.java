package com.se.management.mapper;

import com.se.management.domain.Address;
import com.se.management.model.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mappings({
            @Mapping(target = "state", source = "addressRequest.state")
    })
    Address AddressRequestToAddress(AddressRequest addressRequest);
}
