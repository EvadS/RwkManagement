package com.se.management.model.request;

import com.se.management.model.converters.MessengerTypeConverter;
import com.se.management.model.enums.MessengerType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ContactInfoRequest {

    @NotNull
    @Convert(converter = MessengerTypeConverter.class)
    private MessengerType messengerType;

    @NotBlank
    private String messengerAddress;
}
