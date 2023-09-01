package com.example.jpa.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdate {

  @Size(min = 20, message = "연락처는 최대 20자까지 입력해야 합니다.")
  @NotBlank(message = "연락처는 필수항목입니다.")
  private String phone;


}
