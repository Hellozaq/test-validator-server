package com.fangxia.testvalidator.common.constant;

public class UserConstants {

    private UserConstants() {}

    public static final int ADMIN = 0;
    public static final int TEACHER = 1;
    public static final int ASSISTANT = 2;
    public static final int STUDENT = 3;

    public static final long ADMIN_ACCESS_TOKEN_EXPIRES_TIME = 1000 * 60 * 60 * 24;
    public static final long USER_ACCESS_TOKEN_EXPIRES_TIME = 1000 * 60 * 30;
    public static final long USER_REFRESH_TOKEN_EXPIRES_TIME = 1000 * 60;

}
