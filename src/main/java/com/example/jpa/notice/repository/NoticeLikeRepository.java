package com.example.jpa.notice.repository;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeLikeRepository extends JpaRepository<Notice, Long> {


}
