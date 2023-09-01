package com.example.jpa.user.Repository;

import com.example.jpa.user.entity.Uuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Uuser, Long> {

}
