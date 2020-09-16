package com.se.management.model.search;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearcherSearch {
    private String fName;
    private String lName;

    private List<String> skills;
    private List<String> contacts;
}
