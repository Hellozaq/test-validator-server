package com.fangxia.testvalidator.common.model.eo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "Base Entity")
public abstract class BaseEO {

    @Schema(description = "Primary Key ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @EqualsAndHashCode.Include
    protected String id;

    @Schema(description = "Record creation timestamp")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    @Schema(description = "Record last update timestamp")
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedTime;

}
