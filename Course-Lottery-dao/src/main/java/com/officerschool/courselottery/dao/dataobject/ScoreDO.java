package com.officerschool.courselottery.dao.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_score")
public class ScoreDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer teacher_id;

    private Integer teachingAttitude;

    private Integer teachingContent;

    private Integer teachingDesign;

    private Integer teachingMethod;

    private Integer learningEffect;

    private Integer overallScore;

    private String commitId;

    private Timestamp commitTime;

}
