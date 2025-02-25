package org.example.expert.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.dto.response.UserRoleChangeResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public UserRoleChangeResponse changeUserRole(long userId, UserRoleChangeRequest userRoleChangeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));

        String token = jwtUtil.createToken(userId, user.getEmail(), user.getUserRole());

        return new UserRoleChangeResponse(token);
    }
}
