package com.vue_project.demo.controller; // 패키지 변경

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vue_project.demo.config.JwtTokenProvider;
import com.vue_project.demo.dto.LoginRequestDto; // 임포트 변경
import com.vue_project.demo.dto.LoginResponseDto; // 임포트 변경
import com.vue_project.demo.entity.User; // 임포트 변경
import com.vue_project.demo.service.UserService; // 임포트 변경

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        // 사용자 찾기
        User user = userService.findByUserId(loginRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));
        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        
        // 권한 정보 설정
        List<String> roles = new ArrayList<>();
        roles.add(user.getAuth());
        
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUserId(), roles);
        
        // 응답 데이터 구성 (토큰과 사용자 정보 모두 반환)
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", LoginResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .auth(user.getAuth())
                .success(true)
                .message("로그인 성공")
                .build());
        
        return ResponseEntity.ok(response);
    }

}