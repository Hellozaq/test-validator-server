package com.fangxia.testvalidator.coursemanager.service.impl;

import com.fangxia.testvalidator.coursemanager.mapper.CourseMapper;
import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;
import com.fangxia.testvalidator.coursemanager.service.CourseIService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl
    extends ServiceImpl<CourseMapper, CourseEO>
    implements CourseIService {
}
