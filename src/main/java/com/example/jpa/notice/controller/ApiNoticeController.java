package com.example.jpa.notice.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.exception.AlreadyDeletedException;
import com.example.jpa.notice.exception.DuplicateNoticeException;
import com.example.jpa.notice.exception.NoticeNotFountException;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.repository.NoticeRepository;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;
	
	/*
	@GetMapping("/api/notice")
	public String noticeString() {
		return "공지사항입니다.";
	}

	@GetMapping("/api/notice")
	public NoticeModel notice() {
		LocalDateTime regDate = LocalDateTime.of(2021, 2, 8, 0, 0);
		NoticeModel notice = new NoticeModel();
		notice.setId(1);
		notice.setTitle("공지사항입니다.");
		notice.setContents("공지사항 내용입니다.");
		notice.setRegDate(regDate);

		return notice;
	}

	@GetMapping("/api/notice")
	 public List<NoticeModel> notice(){
		List<NoticeModel> noticeList = new ArrayList<>();

		NoticeModel notice1 = new NoticeModel();
		notice1.setId(1);
		notice1.setTitle("공지사항입니다.");
		notice1.setContents("공지사항 내용입니다.");
		notice1.setRegDate(LocalDateTime.of(2021, 1, 30, 0, 0));
		noticeList.add(notice1);


		noticeList.add(NoticeModel.builder()
									.id(2)
									.title("두번째 공지사항입니다.")
									.contents("두번째 공지사항 내용입니다.")
									.regDate(LocalDateTime.of(2021, 1, 31, 0, 0))
									.build());

		return noticeList;

	 }


    @GetMapping("/api/notice")
    public List<NoticeModel> notice(){
        List<NoticeModel> noticeList= new ArrayList<>();

        // 참고로 null을 리턴하면 empthy로 나온다.
        return noticeList;

    }

    @GetMapping("/api/notice/count")
    public int noriceCount(){
        return 10;
    }

    @PostMapping("/api/notice1")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents) {
        NoticeModel notice = NoticeModel.builder()
                                        .id(3)
                                        .title(title)
                                        .contents(contents)
                                        .regDate(LocalDateTime.now())
                                        .build();
        return notice;
    }


    @PostMapping("/api/notice")
    public NoticeModel addNotice(NoticeModel noticeModel){
        noticeModel.setId(4);
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }



    @PostMapping("/api/notice")
    public  NoticeModel addNotice(@RequestBody NoticeModel noticeModel){
        noticeModel.setId(5);
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }



    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput){
        Notice notice = Notice.builder()
                                .title(noticeInput.getTitle())
                                .contents(noticeInput.getContents())
                                .regDate(LocalDateTime.now())
                                .build();

        noticeRepository.save(notice);
        return notice;
    }




    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput){
        Notice notice = Notice.builder()
                                .title(noticeInput.getTitle())
                                .contents(noticeInput.getContents())
                                .regDate(LocalDateTime.now())
                                .hits(0)
                                .likes(0)
                                .build();

        Notice resultNotice = noticeRepository.save(notice);
        return resultNotice;
    }




    // 동적으로 값을 받는 방법
    // PathVariable : 주소에서 값을 가져올 때
    // RequestBody : json 형태의 body 데이터를 받을 때
    // RequestBody : form 형태의 submt 혹은 param
    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable long id) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            // 값이 있을 때 리턴
            return notice.get();
        }
        
        return null;
    }



    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput){
        Optional<Notice> notice = noticeRepository.findById(id);

        if(notice.isPresent()) {
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setContents(noticeInput.getContents());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
        }
    }


	 */

    @ExceptionHandler(NoticeNotFountException.class)
    public ResponseEntity<String> handlerNoticeNotFountException(NoticeNotFountException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput){
        /*
        Optional<Notice> notice = noticeRepository.findById(id);

        if (!notice.isPresent()) {
            // 게시글이 없을 때 (에외)
                throw new NoticeNotFountException("공지사항에 글이 존재하지 않습니다.");
        }

        // 게시글이 있을 때
        notice.get().setTitle(noticeInput.getTitle());
        notice.get().setContents(noticeInput.getContents());
        notice.get().setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice.get());

         */

        // 위와 같은 로직
        Notice notice = noticeRepository.findById(id).orElseThrow(()-> new NoticeNotFountException("공지사항에 글이 존재하지 않습니다."));


        // 게시글이 있을 때
        notice.setTitle(noticeInput.getTitle());
        notice.setContents(noticeInput.getContents());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);

    }
    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFountException("공지사항에 글이 존재하지 않습니다."));
        notice.setHits(notice.getHits() + 1);
        noticeRepository.save(notice);
    }

    /*
    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFountException("공지사항에 글이 존재하지 않습니다."));
        noticeRepository.delete(notice);

    }

     */

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFountException("공지사항에 글이 존재하지 않습니다."));

        if (notice.isDeleted()) {
            // 이미 삭제된 경우
            throw new AlreadyDeletedException("이미 삭제된 글입니다.");
        }


        // 삭제플래그
        notice.setDeleted(true);
        notice.setDeletedDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    /*
    @DeleteMapping("/api/notice")
    public void deleteNoticeList(@RequestBody NoticeDeleteInput noticeDeleteInput) {
        // 커스텀함수 사용
        List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(() -> new NoticeNotFountException("공지사항에 글이 존재하지 않습니다."));


        noticeList.stream().forEach(e -> {
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);

    }



    @DeleteMapping("/api/notice/all")
    public void deleteAll(){
        noticeRepository.deleteAll();
    }




    @PostMapping("/api/notice")
    public void addNotice(@RequestBody NoticeInput noticeInput){

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);
    }


     */

/*
    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput, Errors errors){


        // 에러 잡기
        if (errors.hasErrors()) {
            */
/*(에러객체를 통째로 던져줌)
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
             *//*


            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().stream().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);

        }

        // 정상적인 저장 로직
        noticeRepository.save(Notice.builder()
                        .title(noticeInput.getTitle())
                        .contents(noticeInput.getContents())
                        .hits(0)
                        .likes(0)
                        .regDate(LocalDateTime.now())
                        .build());

        return ResponseEntity.ok().build();
    }
*/

/*

    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput, Errors errors){


        // 에러 잡기
        if (errors.hasErrors()) {
            *//*(에러객체를 통째로 던져줌)
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
             *//*

            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().stream().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);

        }

        // 정상적인 저장 로직
        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> noticeLatest(@PathVariable int size) {
        // regDate를 기준으로 0~size개 DESC로 정렬
        Page<Notice> noticeList =
        noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));

        return noticeList;
    }*/

    @ExceptionHandler(DuplicateNoticeException.class)
    public ResponseEntity<?> handlerDuplicateNoticeException(DuplicateNoticeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/api/notice")
    public void addNotice(@RequestBody @Valid NoticeInput noticeInput, Errors errors){
        // 중복체크

        // 현재시간 - 1 < 등록시간 ( 1분 미만에 동일한 글을 올림)
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

        //  이 데이터가 있으면 1분내에 여러 건 등록 -> 에러
        int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
                noticeInput.getTitle()
                , noticeInput.getContents()
                , checkDate);


        if (noticeCount > 0){
                // 이 조건의 데이터가 1개 이상 있으면 오류
                throw new DuplicateNoticeException("1분 이내에 등록된 동일한 내용의 공지사항이 있습니다.");
            }



        // 정상적인 저장 로직
        noticeRepository.save(Notice.builder()
                        .title(noticeInput.getTitle())
                        .contents(noticeInput.getContents())
                        .hits(0)
                        .likes(0)
                        .regDate(LocalDateTime.now())
                        .build());
    }


}
