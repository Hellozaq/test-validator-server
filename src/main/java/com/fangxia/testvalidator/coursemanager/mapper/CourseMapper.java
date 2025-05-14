package com.fangxia.testvalidator.coursemanager.mapper;

import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<CourseEO> {

    List<String> selectActiveCourseByUserId(@Param("userId") String userId);

}
