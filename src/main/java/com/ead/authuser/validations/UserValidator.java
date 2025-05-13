package com.ead.authuser.validations;

import com.ead.authuser.controller.AuthenticationController;
import com.ead.authuser.dto.response.UserRecordDto;
import com.ead.authuser.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final Validator validator;
    final UserService userService;

    public UserValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRecordDto userRecordDto = (UserRecordDto) o;
        validator.validate(userRecordDto, errors);
        if (!errors.hasErrors()) {
            validateUserName(userRecordDto, errors);
            validateEmail(userRecordDto, errors);
        }
    }

    private void validateUserName(UserRecordDto userRecordDto, Errors errors) {
        if (userService.existsByUserName(userRecordDto.username())) {
            errors.rejectValue("name", "UserNameConflict", "UserName is already taken");
            logger.error("Error validation UserName", userRecordDto.username());
        }
    }

    private void validateEmail(UserRecordDto userRecordDto, Errors errors) {
        if (userService.existsByEmail(userRecordDto.email())) {
            errors.rejectValue("email", "emailConflict", "Error: Email is already taken!");
            logger.error("Error validation email: {}", userRecordDto.email());
        }
    }
}
