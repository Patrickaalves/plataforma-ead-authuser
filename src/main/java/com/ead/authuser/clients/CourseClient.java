package com.ead.authuser.clients;


import com.ead.authuser.dto.request.CourseRecordDto;
import com.ead.authuser.dto.request.ResponsePageDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component // Para o spring gerenciar esse bean
public class CourseClient {

    Logger logger = LogManager.getLogger(CourseClient.class);

    @Value("${ead.api.url.course}")
    String baseUrlCourse;

    final RestClient restClient;

    public CourseClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<CourseRecordDto> getAllCoursesByUserId(UUID userId,
                                                       Pageable pageable) {
        String url = UriComponentsBuilder
                .fromUriString(baseUrlCourse)
                .pathSegment("courses")
                .queryParam("userId", userId)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", pageable.getSort().stream()
                        .map(order -> order.getProperty() + "," + order.getDirection())
                        .toArray())
                .toUriString();
        logger.debug("metodo getAllCoursesByUserId, url gerada: " + url);
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<CourseRecordDto>>() {});
        } catch (RestClientException e) {
            logger.error("Error request restclient with cause: {}", e.getMessage());
            throw new RuntimeException("Error request RestClient", e);
        }
    }
}
