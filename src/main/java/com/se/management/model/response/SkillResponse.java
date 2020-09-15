package com.se.management.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SkillResponse {
    @NotNull
    private String skillName;

    private byte score;
}
