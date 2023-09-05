package com.example.jpa.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputPassword {
  
  @NotBlank(message = "현재 비밀번호는 필수 항목 입니다.")
  private String newPassword;
  
  @Size(min = 4, max = 20, message = "신규 비밀번호는 4~20 사이의 길이로 입력해주세요")
  @NotBlank(message = "신규 비밀번호는 필수 항목 입니다.")
  private String password;



}
