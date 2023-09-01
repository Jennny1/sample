package com.example.jpa.notice.model;

import lombok.Data;

import java.util.List;

// 삭제 아이디 목록
@Data
public class NoticeDeleteInput {

    private List<Long> idList;
}
