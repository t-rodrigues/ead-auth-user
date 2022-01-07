package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.UtilsService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final UtilsService utilsService;

    public UserClient(RestTemplate restTemplate, UtilsService utilsService) {
        this.restTemplate = restTemplate;
        this.utilsService = utilsService;
    }

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };
            String url = this.utilsService.createUrl(userId, pageable);
            ResponseEntity<ResponsePageDto<CourseDto>> result = this.restTemplate.exchange(url, HttpMethod.GET, null,
                    responseType);

            searchResult = result.getBody().getContent();
        } catch (Exception e) {
            System.out.println("Deu ruim aqui {}");
        }

        return new PageImpl<>(searchResult);
    }

}
