package com.bitstudy.app.repository;

import com.bitstudy.app.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

/** TDD를 위해서 임시로 만들어놓은 저장소 (이거로 DB에 접근할거다)
 *
 * - TDD 만드는 방법
 * 1) 우클릭  > Go to > Test (ctrl + shift + T)
 * 2) JUint5 버전인지 확인
 * */
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
