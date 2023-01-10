package com.bitstudy.app.controller;


import com.bitstudy.app.domain.type.SearchType;
import com.bitstudy.app.dto.response.ArticleResponse;
import com.bitstudy.app.dto.response.ArticleWithCommentsResponse;
import com.bitstudy.app.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 뷰 엔드포인트 관련 컨트롤러
 *
 * 엑셀 api 에 보면 정의해놓은 view 부분에 url 들이 있다. 그거 보면서 하면됨
    /articles	                    GET	게시판 페이지
    /articles/{article-id}	        GET	게시글 페이지
    /articles/serach	            GET	게시판 검색 전용 페이지
    /articles/serach-hashtag	    GET	게시판 해시태그 검색 전용 페이지
 *

 * */

@RequiredArgsConstructor // 필수 필드에 대한 생성자 자동 생성.
// @RequiredArgsConstructor 는 초기화 되지 않은 final 필드 또는 @NonNull 이 붙은 필드에 대해 생성자 생성 해주는 롬복 애너테이션.

@Controller
@RequestMapping("/articles11") // 모든 경로들은 /articles 로 시작하니까 클래스 레벨에 1차로 @RequestMapping("/articles") 걸어놨음
public class Ex11_5_ArticleController {

    private final ArticleService articleService;

//    public ArticleController(ArticleService articleService) {
//        this.articleService = articleService;
//    }
    /* @RequiredArgsConstructor 로 만들어진 생성자 (여기서는 articleService) 를 얘가 읽어서 정보의 전달을 할 수 있게 한다. */

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            /* 해설:
                @RequestParam: 검색어를 받아야 한다. @RequestParam 를 이용해서 getParameter 를 불러올거고반드시 있지 않아도 된다. 없으면 게시글 전체 조회하면 되니까  SearchType, searchValue 에 (required = false) 달아서 null 들어올 수 있게 걸어놓을수 있다.
                @PageableDefault: 페이징 기본설정 (한페이지에 10개, 작성일 순, 내림차순-최근)
             */
            ModelMap map) {
        /** ModelMap: 테스트파일에서 .andExpect(model().attributeExists("articles")); 를 이용해서 articles 라는 키값으로 데이터를 넣어주기로 했으니까 필요함.

         * Model 과 ModelMap는 같은거임. 차이점은 Model 은 인터페이스, ModelMap 은 클래스(구현체)
         */
        // map.addAttribute("articles", List.of()); // 키: articles, 값: 그냥 list
        map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        // 진짜로 정보를 넣어줘야 하니까 ArticleService.java  에 만들어놓은 searchArticles() 에서드에 값을 넣어주면 된.
        // 그런데 searchArticles() 의 반환탕입은 DTO 인데 dto는 모든 엔티티의 데이터를 다 다루고 있어서, 그걸 한번 더 가공해서 필요한 것들만 쓸거다. 그래서 게시글 내용만 가지고 있는 ArticleResponse 를 사용한다.

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        // article.articleCommentsResponse() 해설: 현재 article에 ArticleCommentsResponse 의 정보가 담겨있으니까 article 안에 있는 articleComments를 꺼내면 된다.
        return "articles/detail";
    }
}
