package com.se.management.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearcherResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private Date reviewDate;

}
