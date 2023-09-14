package com.example.jpa.user.Repository;

import com.example.jpa.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  int countByEmail(String email);
  Optional<User> findByIdAndPassword(long id, String password);
  Optional<User> findByUserNameAndPhone(String userName, String phone);

}
