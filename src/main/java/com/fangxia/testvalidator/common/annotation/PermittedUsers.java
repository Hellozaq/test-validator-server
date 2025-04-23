package com.fangxia.testvalidator.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermittedUsers {
    int[] value();
}
