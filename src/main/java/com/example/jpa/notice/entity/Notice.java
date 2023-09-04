package com.example.jpa.notice.entity;

import com.example.jpa.user.entity.Uuser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Uuser user;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private int hits; // 조회수

    @Column
    private int likes; // 좋아요

    @Column
    private boolean deleted; // 삭제여부

    @Column
    private LocalDateTime deleteDate; // 삭제 날짜

}
