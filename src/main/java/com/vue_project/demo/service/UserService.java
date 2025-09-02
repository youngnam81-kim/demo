package com.vue_project.demo.service; // 패키지 변경

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vue_project.demo.dto.LoginRequestDto; // 임포트 변경
import com.vue_project.demo.dto.LoginResponseDto; // 임포트 변경
import com.vue_project.demo.entity.User; // 임포트 변경
import com.vue_project.demo.repository.UserRepository; // 임포트 변경

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return userRepository.findByUserId(loginRequestDto.getUserId())
                .filter(user -> user.getPassword().equals(loginRequestDto.getPassword()))
                .map(user -> LoginResponseDto.builder()
                        .id(user.getId())
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .auth(user.getAuth())
                        .success(true)
                        .message("로그인 성공")
                        .build())
                .orElse(LoginResponseDto.builder()
                        .success(false)
                        .message("아이디 또는 비밀번호가 일치하지 않습니다.")
                        .build());
    }
    
    public boolean register(User user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}