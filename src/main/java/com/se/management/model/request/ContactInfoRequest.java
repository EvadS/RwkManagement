package com.se.management.model.request;

import com.se.management.validation.annotation.MessengerTypeExists;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ContactInfoRequest {
    @NotNull
    @MessengerTypeExists
    private Long messengerTypeId;

    @NotBlank
    private String address;
}
