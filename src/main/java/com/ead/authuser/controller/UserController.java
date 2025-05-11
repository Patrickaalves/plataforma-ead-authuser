package com.ead.authuser.controller;

import com.ead.authuser.dto.UserRecordDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       Pageable pageable,
                                                       @RequestParam(required = false) UUID courseId) {
        Page<UserModel> userModelPage = (courseId != null)
                ? userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable)
                : userService.findAll(spec, pageable);

        if(!userModelPage.isEmpty()) {
            for (UserModel userModel : userModelPage.toList()) {
                userModel.add(linkTo(methodOn(UserController.class).getOneUser(userModel.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(optionalUserModel.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        logger.debug("DELETED deleteUser userId received {}", userId);
        userService.delete(userService.findById(userId).get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserRecordDto.UserView.UserPut.class)
                                             @JsonView(UserRecordDto.UserView.UserPut.class)
                                             UserRecordDto userRecordDto) {

        logger.debug("PUT updateUser userRecordDto received {}", userRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(userRecordDto, userService.findById(userId).get()));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updateUserPassword(@PathVariable(value = "userId") UUID userId,
                                                     @RequestBody @Validated(UserRecordDto.UserView.PasswordPut.class)
                                                     @JsonView({UserRecordDto.UserView.PasswordPut.class})
                                                     UserRecordDto userRecordDto) {
        logger.debug("PUT updateUserPassword userID received {}", userId);
        logger.debug("PUT updateUserPassword UserRecordDto received {}", userRecordDto);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.get().getPassword().equals(userRecordDto.oldPassword())) {
            logger.debug("Mismatched old password! userId", userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password");
        }
        userService.udpatePassword(userRecordDto, userModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody @Validated(UserRecordDto.UserView.ImagePut.class)
                                              @JsonView({UserRecordDto.UserView.ImagePut.class}) UserRecordDto userRecordDto) {
        logger.debug("PUT updateImage userRecordDto received {}", userRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateImage(userRecordDto, userService.findById(userId).get()));
    }
}
