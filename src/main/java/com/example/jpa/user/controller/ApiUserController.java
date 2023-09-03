package com.example.jpa.user.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.Repository.UserRepository;
import com.example.jpa.user.entity.Uuser;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.UserInput;
import com.example.jpa.user.model.UserResponse;
import com.example.jpa.user.model.UserUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiUserController {
    // 사용자 입력값: 이메일(ID), 이름, 비밀번호, 연락처

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
/*
유저 정보 등록
 */
/*
    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        // 에러처리
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));

            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

        }
        return ResponseEntity.ok().build();
        //     new ResponseEntity<>(HttpStatus.OK);
    }

*/
/*
유저 정보 등록
 */

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        // 에러처리
        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            // 에러가 있을 때 처리
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));

            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        Uuser user = Uuser.builder()
                    .email(userInput.getEmail())
                    .userName(userInput.getUserName())
                    .password(userInput.getPassword())
                    .phone((userInput.getPhone()))
                    .regDate(LocalDateTime.now())
                    .build();

        userRepository.save(user);

        return ResponseEntity.ok().build();
        //     new ResponseEntity<>(HttpStatus.OK);
    }




    /*
    유저정보 수정
     */

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate, Errors errors) {

        // 에러처리
        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            // 에러가 있을 때 처리
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));

            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }



        // 사용자 정보가 없을 때 UserNotFountException 발생// "사용자 정보가 없습니다."
        Uuser user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        user.setPhone(userUpdate.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
    사용자 정보 조회 (가입한 아이디에 대한)
    보안상 비밀번호, 가입일, 수정일은 x
     */

    @GetMapping("/api/user/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        Uuser user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // UserResponse userResponse = new UserResponse(user);
        UserResponse userResponse = UserResponse.of(user);

        return userResponse;

    }
    /*
    공지사항 목록
     */
    @GetMapping("/api/user/{id}/notice")
    public List<Notice> userNotice(@PathVariable Long id){
        Uuser user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(user);

        return noticeList;
    }
}
