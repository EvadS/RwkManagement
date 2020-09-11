package com.se.management.domain;

import com.se.management.model.converters.MessengerTypeConverter;
import com.se.management.model.enums.MessengerType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@Entity
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Convert(converter = MessengerTypeConverter.class)
    private MessengerType messengerType;

    @Min(0)
    @Max(10)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ContactInfo() {
    }


}
