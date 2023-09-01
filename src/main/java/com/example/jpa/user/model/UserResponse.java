package com.example.jpa.user.model;

import com.example.jpa.user.entity.Uuser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private long id;
  private String email;
  private String userName;
  protected String phone;

  public UserResponse(Uuser user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.userName = user.getUserName();
    this.phone = user.getPhone();
  }

  public static UserResponse of(Uuser user){
    return UserResponse.builder()
                      .id(user.getId())
                      .email(user.getEmail())
                      .userName(user.getUserName())
                      .phone(user.getPhone())
                      .build();

  }
}
