package com.fangxia.testvalidator.usermanager.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {

    @Schema(description = "Unique login username")
    private String username;

    @Schema(description = "Unique login email")
    private String email;

    @Schema(description = "Display name")
    private String displayName;

    @Schema(description = "User type")
    private int userType;



}
