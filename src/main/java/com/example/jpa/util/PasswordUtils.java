package com.example.jpa.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

  // 패스워드를 암호화해서 리턴
  // 입력한 패스워드를 해시된 패스워드와 비교
  public static boolean equalPassword(String password, String encryptedPassword) {

    return BCrypt.checkpw(password, encryptedPassword);
  }

}
