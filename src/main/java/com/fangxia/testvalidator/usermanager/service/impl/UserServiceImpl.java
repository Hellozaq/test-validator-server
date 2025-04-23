package com.fangxia.testvalidator.usermanager.service.impl;

import com.fangxia.testvalidator.usermanager.mapper.UserMapper;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.service.UserIService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
    extends ServiceImpl<UserMapper, UserEO>
    implements UserIService {
}
