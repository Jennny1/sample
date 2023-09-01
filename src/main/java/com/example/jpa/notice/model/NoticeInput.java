package com.example.jpa.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class NoticeInput {

	@Size(min = 10, max = 100, message = "제목은 10자 이상, 100자 이하로 정해주세요")
	@NotBlank(message = "제목은 필수 항목입니다.")
	private String title;


	@Size(min = 50, max = 1000, message = "내용은 50자 이상, 1000자 이하로 정해주세요")
	@NotBlank(message = "내용은 필수 항목입니다.")
	private String contents;


}
