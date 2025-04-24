package com.fangxia.testvalidator.coursemanager.service.impl;

import com.fangxia.testvalidator.common.util.EntityUtil;
import com.fangxia.testvalidator.coursemanager.mapper.CourseMapper;
import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;
import com.fangxia.testvalidator.coursemanager.model.vo.CourseVO;
import com.fangxia.testvalidator.coursemanager.service.CourseIService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangxia.testvalidator.usermanager.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl
        extends ServiceImpl<CourseMapper, CourseEO>
        implements CourseIService {

    private final UserMapper userMapper;

    @Override
    public Page<CourseVO> pageAdminCourse(int page, int size) {
        LambdaQueryWrapper<CourseEO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CourseEO::getUpdatedTime);

        Page<CourseEO> eoPage = Page.of(page, size, true);

        Page<CourseEO> selectedPage = baseMapper.selectPage(eoPage, wrapper);

        List<CourseVO> voList = selectedPage.getRecords().stream()
                .map(eo -> EntityUtil.eoToVo(eo, userMapper))
                .toList();

        Page<CourseVO> voPage = new Page<>();
        voPage.setCurrent(selectedPage.getCurrent());
        voPage.setSize(selectedPage.getSize());
        voPage.setTotal(selectedPage.getTotal());
        voPage.setRecords(voList);

        return voPage;
    }
}
