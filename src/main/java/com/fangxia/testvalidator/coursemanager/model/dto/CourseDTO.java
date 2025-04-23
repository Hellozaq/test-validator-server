package com.fangxia.testvalidator.coursemanager.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Course DTO")
public class CourseDTO {

    @Schema(description = "Owner username")
    private String username;

    @Schema(description = "Course name")
    private String courseName;

    @Schema(description = "Course description")
    private String courseDescription;

    @Schema(description = "Is the course open")
    private Boolean isOpen;

}
