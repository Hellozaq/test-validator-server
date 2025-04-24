package com.fangxia.testvalidator.coursemanager.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Course VO")
public class CourseVO {

    @Schema(description = "Course name")
    private String courseName;

    @Schema(description = "Course description")
    private String courseDescription;

    @Schema(description = "Owner Name")
    private String courseOwnerName;

    @Schema(description = "Is the course active")
    private Boolean isActive;

    @Schema(description = "Is the course open")
    private Boolean isOpen;

    @Schema(description = "Join code for the course")
    private String courseJoinCode;

}
