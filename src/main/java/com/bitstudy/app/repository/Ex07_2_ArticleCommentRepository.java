package com.bitstudy.app.repository;

import com.bitstudy.app.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** 
 * 
 *  HAL 확인해보기 서비스 실행 하고, 브라우저에서 localhost:8080/api 넣기
 *  
 *  테스트 만들기 (test > controller > Ex07_3_DataRestTest_실패하는테스트.java)
 *
 * */

@RepositoryRestResource
public interface Ex07_2_ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
