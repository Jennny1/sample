package com.example.jpa.notice.entity;

import com.example.jpa.user.entity.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @JoinColumn
  @ManyToOne
  private Notice notice;

  @JoinColumn
  @ManyToOne
  private User user;


}
