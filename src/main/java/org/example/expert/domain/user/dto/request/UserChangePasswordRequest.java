package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    // 조건 분리
//    @Size(min = 8, message = "비밀번호 길이는 8자리 이상이어야 합니다")
//    @Pattern(regexp = ".*\\d.*", message = "숫자를 포함해야 합니다")
//    @Pattern(regexp = ".*[A-Z].*", message = "대문자를 포함해야 합니다")
    // 조건 통합 처리
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,}$", message = "새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
    @NotBlank
    private String newPassword;
}
