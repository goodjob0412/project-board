package com.bitstudy.app.domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@Entity
@Getter
@ToString
public class Ex02_2_ArticleComment_바인딩_설정 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 게시글 고유 아이디

    @Setter
    @ManyToOne(optional = false) // 필수 값이라는 뜻
    private Article article;
    /* 연관관계 맵핑
          연관관계 없이 만들면 private Long articleId; 이런식으로 (관계형 데이터베이스 스타일) 하면 된다.
          그런데 우리는 Article 과 ArticleComment 가 관계를 맺고 있는걸 객체지향적으로 표현하려고 이렇게 쓸거다.
          그러기 위해서 필요한 건 단방향 이라는 뜻의 @ManyToOne 어노테이션을 써주고,
          이건 필수 값이다. 라는 뜻으로 (optional = false) 걸어주기
          댓글은 여러개 : 게시글 1개  == N : 1 == ManyToOne
    */


    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

    //메타데이터
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일자

    @CreatedBy
    @Column(nullable = false,length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일자

    @LastModifiedBy
    @Column(nullable = false,length = 100)
    private String modifiedBy; // 수정자


}
