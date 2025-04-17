package com.fangxia.testvalidator.usermanager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDTO {

    @Schema(description = "Unique login username")
    private String username;

    @Schema(description = "Login password")
    private String password;

    @Schema(description = "Display name")
    private String displayName;

    @Schema(description = "Unique login email")
    private String email;

    @Schema(description = "Verification code")
    private String verificationCode;

    @Schema(description = "User type")
    private int userType;

    @Schema(description = "Login user - email or username")
    private String loginUser;

}
