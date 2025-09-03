package com.vue_project.demo.controller; // 패키지 변경

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vue_project.demo.dto.LoginRequestDto; // 임포트 변경
import com.vue_project.demo.dto.LoginResponseDto; // 임포트 변경
import com.vue_project.demo.entity.User; // 임포트 변경
import com.vue_project.demo.service.UserService; // 임포트 변경

@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = userService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean isRegistered = userService.register(user);
        if (isRegistered) {
            return ResponseEntity.ok().body("회원가입 성공");
        } else {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }
    }
}