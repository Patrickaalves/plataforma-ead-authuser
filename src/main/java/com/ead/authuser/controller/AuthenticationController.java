package com.ead.authuser.controller;

import com.ead.authuser.dto.UserRecordDto;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                                   @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto) {
        if (userService.existsByUserName(userRecordDto.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
        }
        if (userService.existsByEmail(userRecordDto.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: email is already taken!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRecordDto));
    }

    @PostMapping("/manyusers")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @JsonView(UserRecordDto.UserView.RegistrationPost.class) List<UserRecordDto> userRecordDtoList) {
        List<ResponseEntity<Object>> usersCreated = new ArrayList<>();
        List<ResponseEntity<Object>> usersNotCreated = new ArrayList<>();
        for (UserRecordDto userRecordDto : userRecordDtoList) {
            if (userService.existsByUserName(userRecordDto.username())) {
                usersNotCreated.add(ResponseEntity.status(HttpStatus.CONFLICT).body(" Error: Username: " + userRecordDto.username() + " is already taken!"));
            } else if (userService.existsByEmail(userRecordDto.email())) {
                usersNotCreated.add(ResponseEntity.status(HttpStatus.CONFLICT).body(" Error: email: " + userRecordDto.email() + "is already taken!"));
            } else {
                usersCreated.add(ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRecordDto)));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Users created successfully: " + usersCreated + "\nUsers not created! " + usersNotCreated);
    }
}
