package com.bitstudy.app.domain;



/* 할 일 : Lombok 사용하기
 *  주의 : maven 때랑 같은 방식인 것들도 이름이 다르게 되어 있으니 헷갈리지 않게 주의
 *
 *  순서
 *  1) Lombok 을 이용해서 클래스를 엔티티로 변경 @Entity
 *  2) getter/setter, toString 등의 Lombok annotation 사용
 *  3) 동등성, 동일성 비교 할 수 있는 코드 넣어볼 예정
 * */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** @Table - 엔티티와 매핑할 정보를 지정하고
사용법 : @Index(name ="원하는 명칭", columnList = "사용할 테이블명")
name 부분을 생략하면 원래 이름 사용.

 @Index - 데이터베이스 인덱스는 추가, 쓰기 및 저장 공간을 희생해서 테이블에 대한 데이터 검색 속도를 향상시키는 데이터 구조
 사용법 : @Entity 와 세트로 사용

 */

@EntityListeners(AuditingEntityListener.class) /* 이거 없으면 테스트 할때 createdAt 때문에 에러남(Ex04 관련)*/
@Table(indexes = {
        @Index(columnList = "title"),  // 검색속도 빠르게 해주는 작업
        @Index(columnList = "hashtag"),
        @Index(columnList = "createAt"),
        @Index(columnList = "createBy")
})
@Entity // Lombok 을 이용해서 클래스를 엔티티로 변경 @Entity 가 붙은 클래스는 JPA 가 관리하게 된다.
@Getter // 모든 필드의 getter 들이 생성
@ToString // 모든 필드의 toString 생성
public class Article {

    @Id // 전체 필드중에서 PK 표시 해주는 것 @Id 가 없으면 @Entity 어노테이션을 사용 못함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드가 auto_increment 인 경우 @GeneratedValue 를 써서 자동으로 값이 생성되게 해줘야 한다. (기본키 전략)
    private long id; // 게시글 고유 아이디

    /*
      @Setter 도 @Getter 처럼 클래스 단위로 걸 수 있는데, 그렇게 하면 모든 필드에 접근이 가능해진다.
      그런데 id 같은 경우에는 내가 부여하는게 아니라 JPA 에서 자동으로 부여해주는 번호이다.
      메타 데이터들도 자동으로 JPA 가 세팅 되게 만들어야 한다. 그래서 id 와 메타데이터는 @Setter 가 필요 없다.
      @Setter 의 경우에는 지금처럼 필요한 필드에만 주는걸 연습하자.(요건 강사님 스타일)

    */

    /**
     @Column - 해당 컬럼이 not null 인 경우 @Column(nullable =false) 써준다.
     기본 값은 true 라서 @Column 을 아예 안쓰면 null 가능
     사용법 : @Column(nullable = false, length = 숫자) 숫자 안쓰면 기본 값 255 적용
     */
    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //메타데이터
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createAt; // 생성일자

    @CreatedBy
    @Column(nullable = false,length = 100)
    private String createBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일자

    @LastModifiedBy
    @Column(nullable = false,length = 100)
    private String modifiedBy; // 수정자
    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag){
        return new Article(title,content,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
