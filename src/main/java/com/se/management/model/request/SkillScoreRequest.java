package com.se.management.model.request;


import com.se.management.constrains.SkillExists;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


//@Data
//@NoArgsConstructor

public class SkillScoreRequest {

    @NotNull
    @SkillExists
    private Long skillId;

    @Min(0)
    @Max(10)
    private byte score;

    public SkillScoreRequest() {
    }


    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }
}
