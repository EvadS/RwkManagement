package com.se.management.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearcherRequest {

    AddressRequest addressRequest;
    @NotNull
    @Size(max = 100)
    private String firstName;
    @NotNull
    @Size(max = 100)
    private String lastName;
    private Long reviewDate;
    @NotNull

    // TODO: for test
    // @Email
    @Size(max = 100)
    private String email;
    private List<SkillRequest> skillRequestList = new ArrayList<>();

    private List<ContactInfoRequest> contactInfos = new ArrayList<>();
}
