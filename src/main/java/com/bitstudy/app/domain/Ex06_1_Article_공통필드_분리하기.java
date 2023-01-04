package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Aricle과 ArticleComment 에 있는 공통 필드(메타데이터. ID제외)들을 별도로 빼서 관리할거임.
 * 이유는 앞으로 Aricle과 ArticleComment 처럼 fk 같은거로 엮여있는 테이블들 만들경우, 모든 domain 안에 있는 파일들에 많은 중복 코드들이 들어가게 된다. 그래서 별도의 파일에 공통되는 것들을 다 몰아넣고 사용하는거 해보기

 * 참고: 공통필드를 빼는걸 팀마다 다른다.
 *      중복코드를 싫어해서 한 파일에 다 몰아두는 사람들이 있고,
 *      (유지보수)
 *
 *      중보코드를 괜찮아 해서 각 파일에 그냥 두는 사람도 있다.
 *      (각 파일에 모든 정보다 다 있다. 변경시 유연하게 코드 작업을 할 수 있다)
 *
 * 추출은 두가지 방법으로 할 수 있다.
 *  1) @Embedded - 공통되는 필드들을 하나의 클래스로 만들어서 @Embedded 있는 곳에서 치환 하는 방식
 *
 *  2) @MappedSuperClass - (요즘 실무에서 사용)
 *              @MappedSuperClass 어노테이션이 붙은곳에서 사용
 *
 *  * 둘의 차이: 사실은 둘이 비슷 하지만 @Embedded 방식을 하게 되면 필드가 하나 추가된다.
 *              영속성 컨텍스트를 통해서 데이터를 넘겨 받아서 어플리케이션으로 열었을때에는 어짜피 AuditingField 랑 똑같이 보인다. 
 *               (중간에 한단계가 더 있다는 뜻)
 *               
 *               @MappedSuperClass 는 표준 JPA 에서 제공해주는 클래스. 중간단계 따로 없이 바로 동작
 *
 * */

//@EntityListeners(AuditingEntityListener.class) /* 이거 없으면 테스트 할때 createdAt 때문에 에러남(Ex04 관련)*/
@Table(indexes = {
        @Index(columnList = "title"),  // 검색속도 빠르게 해주는 작업
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity // Lombok 을 이용해서 클래스를 엔티티로 변경 @Entity 가 붙은 클래스는 JPA 가 관리하게 된다.
@Getter // 모든 필드의 getter 들이 생성
@ToString // 모든 필드의 toString 생성
public class Ex06_1_Article_공통필드_분리하기 extends AuditingFields {

    @Id // 전체 필드중에서 PK 표시 해주는 것 @Id 가 없으면 @Entity 어노테이션을 사용 못함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드가 auto_increment 인 경우 @GeneratedValue 를 써서 자동으로 값이 생성되게 해줘야 한다. (기본키 전략)
    private long id; // 게시글 고유 아이디

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
/***************************************************/
    /* 1) Embedded 방식 */
//    class Tmp {
//        @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일자
//        @CreatedBy @Column(nullable = false,length = 100) private String createdBy; // 생성자
//        @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일자
//        @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy; // 수정자
//    }
//    @Embedded Tmp tmp;


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
/*************************************************/



    protected Ex06_1_Article_공통필드_분리하기() {}

    private Ex06_1_Article_공통필드_분리하기(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Ex06_1_Article_공통필드_분리하기 of(String title, String content, String hashtag){
        return new Ex06_1_Article_공통필드_분리하기(title,content,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex06_1_Article_공통필드_분리하기 article = (Ex06_1_Article_공통필드_분리하기) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
