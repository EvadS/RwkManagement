package com.se.management.model.request;


import com.se.management.constrains.MessengerTypeExists;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class SkillScoreRequest {

    @NotNull
    @MessengerTypeExists
    private Long skillId;

    @Min(0)
    @Max(10)
    private byte score;
}
