package com.se.management.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearcherListItem {

    private long id;

    private String lName;
    private String fName;

    private List<SkillScoreResponse> tolSkillList = new ArrayList<>();
}
