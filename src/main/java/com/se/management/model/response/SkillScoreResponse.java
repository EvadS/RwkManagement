package com.se.management.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SkillScoreResponse {
    @NotNull
    private String skillName;

    private byte score;
}
