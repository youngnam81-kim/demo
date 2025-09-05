package com.vue_project.demo.dto;
//(선택 사항) 데이터 전송 객체 - API 응답/요청을 위한 모델

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
	private Long id;
	private String userId;
	private String userName;
	private String password;
	private String auth;
	private boolean success;
	private String message;
}