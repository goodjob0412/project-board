package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/** 할일: Lombok 사용하기
 * 주의: maven 때랑 같은 방식인 것들도 이름이 다르게 되어 있으니 헷갈리지 않게 주의!
 * 
 * 순서
 * 1) 롬복을 이용해서 클래스를 엔티티로 변경
 * 2) getter/setter, toString 등의 롬복 어노테이션 사용
 * 3) 동등성, 동일성 비교할 수 있는 코드 넣어볼거임
 * */

@Getter
@Setter
@ToString
@Entity
public class Article {


    private Long id;
    private String title; // 제목
    private String content; // 본문
    private String hashtag; // 해시태그

    // 메타데이터
    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자



}
