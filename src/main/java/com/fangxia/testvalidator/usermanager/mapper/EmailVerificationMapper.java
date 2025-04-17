package com.fangxia.testvalidator.usermanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangxia.testvalidator.usermanager.model.eo.EmailVerificationEO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailVerificationMapper extends BaseMapper<EmailVerificationEO> {

    boolean isTooFrequent(@Param("email") String email);

    @Delete("DELETE FROM fxedu_dev.fx_email_verification WHERE expired_time < NOW()")
    void deleteExpiredCodes();

    @Delete("DELETE FROM fxedu_dev.fx_email_verification WHERE email = #{email}")
    void deleteByEmail(@Param("email") String email);

}
