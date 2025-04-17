package com.fangxia.testvalidator.usermanager.mapper;

import com.fangxia.testvalidator.usermanager.model.eo.UserEO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEO> {
}
