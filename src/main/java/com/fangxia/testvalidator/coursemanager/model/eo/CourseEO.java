package com.fangxia.testvalidator.coursemanager.model.eo;

import com.fangxia.testvalidator.common.model.eo.BaseEO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@TableName("fx_courses")
public class CourseEO extends BaseEO {

    @Schema(description = "Course name")
    @TableField("course_name")
    private String courseName;

    @Schema(description = "Course description")
    @TableField("course_description")
    private String courseDescription;

    @Schema(description = "Owner id")
    @TableField("course_owner")
    private String courseOwner;

    @Schema(description = "Is the course active")
    @TableField("is_active")
    private Boolean isActive;

    @Schema(description = "Is the course open")
    @TableField("is_open")
    private Boolean isOpen;

    @Schema(description = "Join code for the course")
    @TableField("course_join_code")
    private String courseJoinCode;

}
