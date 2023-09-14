package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.entity.NoticeLike;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeLikeRepository;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.Repository.UserRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.ExistsEmailExeption;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.UserInput;
import com.example.jpa.user.model.UserInputFind;
import com.example.jpa.user.model.UserLogin;
import com.example.jpa.user.model.UserLoginToken;
import com.example.jpa.user.model.UserResponse;
import com.example.jpa.user.model.UserUpdate;
import com.example.jpa.util.PasswordUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiUserController {
  // 사용자 입력값: 이메일(ID), 이름, 비밀번호, 연락처

  private final UserRepository userRepository;
  private final NoticeRepository noticeRepository;
  private final NoticeLikeRepository noticeLikeRepository;
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
/*

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

*/
  /*
   *//*
  사용자등록 (이메일 예외처리)
   *//*
  @PostMapping("/api/user")
  public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }

    if (userRepository.countByEmail(userInput.getEmail()) > 0) {
      throw new ExistsEmailExeption("이미 존재하는 이메일");
    }

    User user = User.builder()
        .email(userInput.getEmail())
        .userName(userInput.getUserName())
        .password(userInput.getPassword())
        .phone((userInput.getPhone()))
        .regDate(LocalDateTime.now())
        .build();
    userRepository.save(user);
    return ResponseEntity.ok().build();
  }

  // ExceptionHandler
  @ExceptionHandler(value = {UserNotFoundException.class, ExistsEmailExeption.class,
      PasswordNoMatchException.class})
  public ResponseEntity<?> ExistsEmailExeptionHandler(RuntimeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

  }*/


    /*
    유저정보 수정
     */

  @PutMapping("/api/user/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate,
      Errors errors) {

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
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    user.setPhone(userUpdate.getPhone());
    user.setUpdateDate(LocalDateTime.now());
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }


    /*
    사용자 정보 조회 (가입한 아이디에 대한)
    보안상 비밀번호, 가입일, 수정일은 x
     */

  @GetMapping("/api/user/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    User user = userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // UserResponse userResponse = new UserResponse(user);
    UserResponse userResponse = UserResponse.of(user);

    return userResponse;

  }

  /*
  35. 공지사항 목록
  삭제일, 삭제자 아이디는 보안상 보여주지 않음
  작성자의 아이디와 이름만 보여줌
   */
  @GetMapping("/api/user/{id}/notice")
  public List<NoticeResponse> userNotice(@PathVariable Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    List<Notice> noticeList = noticeRepository.findByUser(user);
    List<NoticeResponse> noticeResponseList = new ArrayList<>();

    noticeList.stream().forEach((e) -> {
      noticeResponseList.add(NoticeResponse.of(e));
    });

    return noticeResponseList;
  }


    /*
    비밀번호 수정
    이전 비밀번호와 일치하는 경우 수정
    일치하지 않는 경우에는 PasswordNoMatchException
    "비밀번호가 일치하지 않습니다."
     */

/*  @PatchMapping("/api/user/{id}/pasword")
  public ResponseEntity<?> updateUserPassword(@PathVariable long id,
      @RequestBody UserInputPassword userInputPassword, Errors errors) {

    // 에러처리
    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      // 에러가 있을 때 처리
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));

      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }

    User user = userRepository.findByIdAndPassword(id, userInputPassword.getPassword())
        .orElseThrow(() -> new PasswordNoMatchException("비밀번호가 일치하지 않습니다."));

    // 기존 비밀번호가 일치하였을 때
    user.setPassword(userInputPassword.getNewPassword());
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }*/

  // 패스워드 암호화
  private String getEncryptPassword(String password) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(password);
  }

  /*
  회원가입 시 비밀번호를 암호화하여 저장
   */
  @PostMapping("/api/user")
  public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }

    if (userRepository.countByEmail(userInput.getEmail()) > 0) {
      throw new ExistsEmailExeption("이미 존재하는 이메일");
    }

    // 패스워드 암호화
    String encryptPassword = getEncryptPassword(userInput.getPassword());
    System.out.println("암호 : " + encryptPassword);

    User user = User.builder()
        .email(userInput.getEmail())
        .userName(userInput.getUserName())
        .password(encryptPassword)
        .phone((userInput.getPhone()))
        .regDate(LocalDateTime.now())
        .build();
    userRepository.save(user);
    return ResponseEntity.ok().build();

  }

  /*
  회원탈퇴
  회원정보가 존재하지 않을 때는 예외처리
  사용자가 등록한 게시글이 있는 경우에는 회원 삭제가 되지 않음
   */
  @DeleteMapping("/api/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable long id) {

    // 사용자 정보가 없을 때 UserNotFountException 발생// "사용자 정보가 없습니다."
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 등록한 게시글이 있는지 확인

        /*
        39. 탈퇴(유저삭제)
        회원정보가 존재하지 않는 경우 예외처리
        만약 회원이 게시판에 글을 쓴 경우에는 회원삭제가 되지 않음
         */

    try {
      userRepository.delete(user);
    } catch (DataIntegrityViolationException e) {
      String message = "제약조건에 문제가 발생하였습니다.";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

    } catch (Exception e) {
      String message = "회원 탈퇴 중 문제가 발생하였습니다.";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok().build();

  }

  /*
  40. 사용자 이메일 검색, 찾기
  이름과 전화번호에 해당하는 이메일을 찾는다.
   */
  @GetMapping("/api/user/")
  public ResponseEntity<?> findUser(@RequestBody UserInputFind userInputFind) {

    // 유저 이름과 폰으로 검색
    User user = userRepository.findByUserNameAndPhone(userInputFind.getUserName(),
            userInputFind.getPhone())
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 검색 결과가 있을 때, 정보 리턴
    UserResponse userResponse = UserResponse.of(user);

    return ResponseEntity.ok().body(userResponse);
  }


    /*
    41. 사용자 비밀번호 초기화 요청
    아이디 정보 조회 후
    비밀번호를 초기화한 이후에
    문자로 전송하는 로직
    초기화 비밀번호는 문자열 10자로 설정
     */


  private String getResetPassword() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

  }

  @GetMapping("/api/user/{id}/password/reset")
  public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {

    // 유저 아이디로 검색
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 비밀번호 초기화
    String resetPassword = getResetPassword();
    String resetEncryptPassword = getEncryptPassword(resetPassword);
    user.setPassword(resetEncryptPassword);
    userRepository.save(user);

    String message = String.format("[%s]님의 임시비밀번호가 [%s]로 초기화 되었습니다."
        , user.getUserName()
        , resetPassword);

    sensSMS(message);

    return ResponseEntity.ok().build();

  }

  void sensSMS(String message) {
    System.out.println("[문자메시지 전송]");
    System.out.println(message);

  }


  /*
  42. 좋아요한 공지사항 글 보기

   */

  @GetMapping("/api/user/{id}/notice/like")
  public List<NoticeLike> likeNotice(@PathVariable Long id) {
    // 유저 아이디로 검색
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);

    return noticeLikeList;


  }


  /*
  43. 사용자 이메일과 비밀번호를 통해서 JWT를 발행
  -JWT 토큰발행시 사용자 정보가 유효하지 않을 때 예외 발생
  -사용자 정보가 존재하지 않는 경우 예외(UserNotFountException)
  -비밀번호가 일치하지 않는 경우 예외(PasswordNotMatchException)
   */

/*

  @PostMapping("/api/user/login")
  public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }


    // 유저 아이디로 검색
    User user = userRepository.findByEmail(userLogin.getEmail())
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 비밀번호 일치여부 확인 (암호화한 비밀번호를 비교)

    if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
      throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
    }

    return ResponseEntity.ok().build();

  }
*/

  /*
  44.사용자의 이메일과 비밀번호를 통해서 JWT 를 발행하는 로직을 작성
  *
   */
/*  @PostMapping("/api/user/login")
  public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }

    // 유저 아이디로 검색
    User user = userRepository.findByEmail(userLogin.getEmail())
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 비밀번호 일치여부 확인 (암호화한 비밀번호를 비교)

    if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
      throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
    }

    // 토큰 발행시점
    String token = JWT.create()
        .withExpiresAt(new Date())
        .withClaim("user_id", user.getId())
        .withSubject(user.getUserName())
        .withIssuer(user.getEmail())
        .sign(Algorithm.HMAC512("fast".getBytes()));

    return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());

  }*/


  /*
  45.JWT 토큰 발행 시 발행 유효기간을 1개월로 저장
   */

  @PostMapping("/api/user/login")
  public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });
      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
    }

    // 유저 아이디로 검색
    User user = userRepository.findByEmail(userLogin.getEmail())
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    // 비밀번호 일치여부 확인 (암호화한 비밀번호를 비교)

    if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
      throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
    }

    // 날짜 지정
    LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
    Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);

    // 토큰 발행시점
    String token = JWT.create()
        .withExpiresAt(expiredDate)
        .withClaim("user_id", user.getId())
        .withSubject(user.getUserName())
        .withIssuer(user.getEmail())
        .sign(Algorithm.HMAC512("fast".getBytes()));

    return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());

  }


}
