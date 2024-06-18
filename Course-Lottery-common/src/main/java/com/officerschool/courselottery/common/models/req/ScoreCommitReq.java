package com.officerschool.courselottery.common.models.req;

import lombok.Data;

@Data
public class ScoreCommitReq {
    private Integer classId;

    private String score;

}
