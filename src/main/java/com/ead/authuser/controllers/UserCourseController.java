package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final UserClient userClient;

    public UserCourseController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<?>> getAllCoursesByUser(@PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable) {
        var courses = userClient.getAllCoursesByUser(userId, pageable);

        return ResponseEntity.ok(courses);
    }

}
