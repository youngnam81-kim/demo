package com.vue_project.demo.dto; // 패키지 변경

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private Long id;
    private String userId;
    private String userName;
    private String auth;
    private boolean success;
    private String message;
}