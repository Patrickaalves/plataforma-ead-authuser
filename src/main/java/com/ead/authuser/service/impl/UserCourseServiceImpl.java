package com.ead.authuser.service.impl;

import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.service.UserCouseService;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCouseService {

    final UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }
}
