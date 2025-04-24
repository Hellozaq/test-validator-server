package com.fangxia.testvalidator.common.util;

import com.fangxia.testvalidator.common.security.BCryptUtil;
import com.fangxia.testvalidator.coursemanager.model.dto.CourseDTO;
import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;
import com.fangxia.testvalidator.coursemanager.model.vo.CourseVO;
import com.fangxia.testvalidator.usermanager.mapper.UserMapper;
import com.fangxia.testvalidator.usermanager.model.dto.UserDTO;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.model.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityUtil {

    public static UserEO dtoToEo(UserDTO userDTO) {
        UserEO userEO = new UserEO();
        userEO.setUsername(userDTO.getUsername());
        userEO.setEmail(userDTO.getEmail());
        userEO.setDisplayName(userDTO.getDisplayName());
        userEO.setUserType(userDTO.getUserType());

        userEO.setPassword(BCryptUtil.encode(userDTO.getPassword()));

        return userEO;
    }

    public static UserVO eoToVo(UserEO userEO) {

        UserVO userVO = new UserVO();
        userVO.setUsername(userEO.getUsername());
        userVO.setEmail(userEO.getEmail());
        userVO.setDisplayName(userEO.getDisplayName());
        userVO.setUserType(userEO.getUserType());
        return userVO;

    }

    public static CourseEO dtoToEo(CourseDTO courseDTO) {
        CourseEO courseEO = new CourseEO();
        courseEO.setCourseName(courseDTO.getCourseName());
        courseEO.setCourseDescription(courseDTO.getCourseDescription());
        courseEO.setIsOpen(courseDTO.getIsOpen());
        return courseEO;
    }

    public static CourseVO eoToVo(CourseEO courseEO, UserMapper userMapper) {
        CourseVO courseVO = new CourseVO();

        courseVO.setCourseName(courseEO.getCourseName());
        courseVO.setCourseDescription(courseEO.getCourseDescription());
        courseVO.setIsActive(courseEO.getIsOpen());
        courseVO.setIsOpen(courseEO.getIsOpen());
        courseVO.setCourseJoinCode(courseEO.getCourseJoinCode());

        UserEO userEO = userMapper.selectById(courseEO.getCourseOwner());
        courseVO.setCourseOwnerName(userEO.getUsername());

        return courseVO;
    }

}
