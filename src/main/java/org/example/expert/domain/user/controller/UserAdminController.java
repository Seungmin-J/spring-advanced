package org.example.expert.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.dto.response.UserRoleChangeResponse;
import org.example.expert.domain.user.service.UserAdminService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PatchMapping("/admin/users/{userId}")
    public UserRoleChangeResponse changeUserRole(@PathVariable long userId, @RequestBody UserRoleChangeRequest userRoleChangeRequest) {
        log.info("changeUserRole 실행");
        return userAdminService.changeUserRole(userId, userRoleChangeRequest);
    }
}
