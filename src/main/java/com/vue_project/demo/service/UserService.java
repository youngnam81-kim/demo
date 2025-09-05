package com.vue_project.demo.service; // 패키지 변경

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vue_project.demo.dto.LoginRequestDto; // 임포트 변경
import com.vue_project.demo.dto.LoginResponseDto; // 임포트 변경
import com.vue_project.demo.dto.UserRequestDto;
import com.vue_project.demo.entity.User; // 임포트 변경
import com.vue_project.demo.repository.UserRepository; // 임포트 변경

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
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
    
    public User insertUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userName(userRequestDto.getUserName())
                .password(passwordEncoder.encode(userRequestDto.getPassword())) // 비밀번호 암호화
                .auth("USER") // 기본 권한 설정
                .build();
        
        return userRepository.save(user);
    }
    
 // 아이디로 사용자 조회
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    
    public boolean existByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
    
}