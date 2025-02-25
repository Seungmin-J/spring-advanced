package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserRoleChangeResponse {

    private final String token;

    public UserRoleChangeResponse(String token) {
        this.token = token;
    }
}
