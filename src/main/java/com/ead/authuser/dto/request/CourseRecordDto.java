package com.ead.authuser.dto.request;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CourseRecordDto(UUID courseId,
                              String name,
                              String description,
                              String imageUrl,
                              CourseStatus courseStatus,
                              UUID userInstructor,
                              CourseLevel courseLevel) {
}
