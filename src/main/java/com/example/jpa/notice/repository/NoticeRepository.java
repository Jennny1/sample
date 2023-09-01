package com.example.jpa.notice.repository;

import com.example.jpa.sample1.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Jpa를 상속받는 interface
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    
    // List로 찾기 (커스텀 함수)
    // List가 없을 수도 있기 떄문에 Optional로 처리
    Optional<List<Notice>> findByIdIn(List<Long> idList);

    //  조건 : 제목 동일, 내용 동일, 등록시간이 체크시간보다 크거나 같다.
    Optional<List<Notice>> findByTitleAndContentsAndRegDateIsGreaterThanEqual(String titme, String contents, LocalDateTime regDate);


    // 개수만 가져오도록 설정할 수도 있음
    int countByTitleAndContentsAndRegDateIsGreaterThanEqual(String titme, String contents, LocalDateTime regDate);
}
