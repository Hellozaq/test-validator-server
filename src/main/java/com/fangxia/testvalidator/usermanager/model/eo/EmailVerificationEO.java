package com.fangxia.testvalidator.usermanager.model.eo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangxia.testvalidator.common.model.eo.BaseEO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@TableName("fx_email_verification")
public class EmailVerificationEO extends BaseEO {

    @Schema(description = "Unique login email")
    @TableField(value = "email")
    private String email;

    @Schema(description = "Verification code")
    @TableField(value = "verification_code")
    private String verificationCode;

    @Schema(description = "Expired timestamp")
    @TableField(value = "expired_time")
    protected LocalDateTime expiredTime;

}
