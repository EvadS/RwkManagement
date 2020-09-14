package com.se.management.mapper;

import com.se.management.domain.ContactInfo;
import com.se.management.model.request.ContactInfoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactInfo ContactInfoRequestToContactInfo (ContactInfoRequest contactInfoRequest);
}
