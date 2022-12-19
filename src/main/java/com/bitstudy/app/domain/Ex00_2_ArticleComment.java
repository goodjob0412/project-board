package com.bitstudy.app.domain;

import java.time.LocalDateTime;

public class Ex00_2_ArticleComment {

    private Long id;

    private Article article;
    /**  연관관계 매핑.
     *  연관관계 없이 만들면 private Long articleId; 이런식으로(관계형 데이터베이스 스타일) 하면된다.
     *  그런데 우리는 Article과 ArticleCommment 가 관계를 맺고 있는걸 객체지향적으로 표현하려고 이렇게 쓸거다. */


    private String content; // 본문


    // 메타데이터
    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
}
