package com.se.management.mapper;

import com.se.management.domain.MessengerType;
import com.se.management.model.request.MessengerTypeRequest;
import com.se.management.model.response.MessengerTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessengerTypeMapper {
    MessengerTypeMapper INSTANCE = Mappers.getMapper(MessengerTypeMapper.class);

    MessengerTypeResponse MessengerTypeToMessengerTypeResponse(MessengerType messengerType);

    MessengerType MessengerTypeRequestToMessengerType(MessengerTypeRequest request);
}
