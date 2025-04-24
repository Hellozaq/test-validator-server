package com.fangxia.testvalidator.coursemanager.service;

import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;
import com.fangxia.testvalidator.coursemanager.model.vo.CourseVO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CourseIService extends IService<CourseEO> {

    Page<CourseVO> pageAdminCourse(int page, int size);

}
