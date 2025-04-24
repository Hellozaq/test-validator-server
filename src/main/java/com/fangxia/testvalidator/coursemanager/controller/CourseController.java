package com.fangxia.testvalidator.coursemanager.controller;

import com.fangxia.testvalidator.common.annotation.PermittedUsers;
import com.fangxia.testvalidator.common.model.ApiResponse;
import com.fangxia.testvalidator.common.util.CodeUtil;
import com.fangxia.testvalidator.common.util.EntityUtil;
import com.fangxia.testvalidator.coursemanager.model.dto.CourseDTO;
import com.fangxia.testvalidator.coursemanager.model.eo.CourseEO;
import com.fangxia.testvalidator.coursemanager.service.CourseIService;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.service.UserIService;

import static com.fangxia.testvalidator.common.constant.ApiConstants.COURSE_URL;
import static com.fangxia.testvalidator.common.constant.UserConstants.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(COURSE_URL)
@RequiredArgsConstructor
public class CourseController {

    private final UserIService userIService;

    private final CourseIService courseIService;

    @PostMapping("/create")
    @Operation(summary = "Create a new Course")
    @PermittedUsers({ADMIN, TEACHER})
    public ApiResponse<?> create(@RequestBody CourseDTO courseDTO) {

        UserEO owner = userIService.getOne(new QueryWrapper<UserEO>()
            .eq("username", courseDTO.getUsername()));

        if(owner == null) {
            return ApiResponse.failure("User does not exist.");
        }

        boolean existsCourse = courseIService.count((new QueryWrapper<CourseEO>()
            .eq("course_name", courseDTO.getCourseName())
            .eq("course_owner", owner.getId()))) > 0;

        if(existsCourse) {
            return ApiResponse.failure("Course name already exist for current user.");
        }

        String joinCode;

        int attempt = 0;
        final int MAX_ATTEMPTS = 10;

        do {
            String generateJoinCode = CodeUtil.generateJoinCode();
            boolean existsCode = courseIService.count(new QueryWrapper<CourseEO>()
                .eq("course_join_code", generateJoinCode)) > 0;
            if(!existsCode) {
                joinCode = generateJoinCode;
                break;
            }
            if (++attempt >= MAX_ATTEMPTS) {
                return ApiResponse.failure("Failed to generate a unique join code after " + MAX_ATTEMPTS + " attempts.");
            }
        } while (true);

        CourseEO course = EntityUtil.dtoToEo(courseDTO);
        course.setId(UUID.randomUUID().toString());
        course.setCourseOwner(owner.getId());
        course.setIsActive(true);
        course.setCourseJoinCode(joinCode);
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());
        courseIService.save(course);

        return ApiResponse.success("Course created successfully.");

    }

    @GetMapping("/page")
    @Operation(summary = "Query 10 courses")
    @PermittedUsers({ADMIN, TEACHER, ASSISTANT, STUDENT})
    public ApiResponse<?> page(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestAttribute("userType") Integer userType
    ) {

        if(userType == ADMIN) {

            return ApiResponse.success(courseIService.pageAdminCourse(page, size));

        } else {
            return ApiResponse.failure("Temp Disabled");
        }

    }

}
