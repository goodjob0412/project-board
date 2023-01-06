package com.bitstudy.app.dto;

import com.bitstudy.app.domain.Article;
import com.bitstudy.app.domain.UserAccount;

import java.time.LocalDateTime;

/** record 란
    자바 16버전에서 새로 나온거.
    DTO랑 비슷. DTO 를 구현하려면 getter, setter, equals, hashcode, toString 같은 데이터 처리를 수행하기 위해 오버라이드 된 메소드를 반복해서 작성해야 한다. 이런것들을 보일러 플레이트 코드(여러곳에서 재사용 되는 반복적으로 비슷한 형태를 가진 코드) 라고 한다.
    롬복을 이용해서 어느정도 중복으로 발생하는 코드를 줄일수 있지만 근본적인 한계는 해결 못함.
    그래서 특정 데이터와 관련 있는 필드들만 묶어놓은 자료구조로 record 라는게 생겼다.
    
 *주의: record는 entity 로 쓸 수 없다. DTO로만 가능.
        이유는 쿼리 결과를 매핑할때 객체를 인스턴스화 할 수 있도록 매개변수가 없는 생성자가 필요하지만, record 에서는 매개변수가 없는 생성자(기본생성자)를 제공하지 않는다. 또한 setter 도 사용할 수 없다. (그래서 모든 필드의 값을 입력한 후에 생성할 수 있다.)
 
 */

public record ArticleDto( /* 우선 엔티티가 가지고 있는 모든 정보를 dto도 가지고 있게 해서 나중에 응답할때 어떤걸 보내줄지 선택해서 가공하게 할거임 */
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
        ) {

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    /* entity를 매개변수로 입력하면 ArticleDto로 변환해주는 메서드.
    *
    * entity를 받아서 new 한 다음에 인스턴스에다가 entity. 이라고 해가면서 맵핑시켜서 return 하고 있는거
    * 맵퍼라고 부름.  */
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    /* 위에거랑 반대. dto 를 주면 엔티티를 생성하는 메서드 */
    public Article toEntity() {
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }
}










