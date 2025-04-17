package com.fangxia.testvalidator.usermanager.model.eo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangxia.testvalidator.common.model.eo.BaseEO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@TableName("fx_users")
public class UserEO extends BaseEO {

    @Schema(description = "Unique login username")
    @TableField(value = "username")
    private String username;

    @Schema(description = "Login password")
    @TableField(value = "password")
    private String password;

    @Schema(description = "Unique login email")
    @TableField(value = "email")
    private String email;

    @Schema(description = "Display name")
    @TableField(value = "display_name")
    private String displayName;

    @Schema(description = "User type")
    @TableField(value = "user_type")
    private int userType;

}
