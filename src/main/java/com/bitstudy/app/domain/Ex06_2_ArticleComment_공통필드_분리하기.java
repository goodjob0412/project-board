package com.bitstudy.app.domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
@Getter
@ToString
public class Ex06_2_ArticleComment_공통필드_분리하기 extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 게시글 고유 아이디

    @Setter
    @ManyToOne(optional = false) // 필수 값이라는 뜻
    private Article article;


    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

//    //메타데이터
//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt; // 생성일자
//
//    @CreatedBy
//    @Column(nullable = false,length = 100)
//    private String createdBy; // 생성자
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일자
//
//    @LastModifiedBy
//    @Column(nullable = false,length = 100)
//    private String modifiedBy; // 수정자


}
