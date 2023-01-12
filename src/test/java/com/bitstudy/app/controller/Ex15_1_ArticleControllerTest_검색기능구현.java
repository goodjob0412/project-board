package com.bitstudy.app.controller;

import com.bitstudy.app.config.SecurityConfig;
import com.bitstudy.app.domain.type.SearchType;
import com.bitstudy.app.dto.ArticleWithCommentsDto;
import com.bitstudy.app.dto.UserAccountDto;
import com.bitstudy.app.service.ArticleService;
import com.bitstudy.app.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class) 
@DisplayName("view 컨트롤러 - 게시글")
class Ex15_1_ArticleControllerTest_검색기능구현 {

    private final MockMvc mvc;
    @MockBean private ArticleService articleService;

    @MockBean private PaginationService paginationService;

    public Ex15_1_ArticleControllerTest_검색기능구현(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


    /**1) 게시판 (리스트) 페이지*/
    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    public void articlesAll() throws Exception {
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) 
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
    public void givenSearchKeyword_thenReturnArticles() throws Exception {
        // Given
        SearchType searchType = SearchType.TITLE; /* 검색어가 있게 할건데 이번에는 제목으로만 테스트 할거임*/
        String searchValue = "title"; /* 키워드*/

        given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
        /* 페이징이 어떻게 나오던 일단 상관없다. 단지 검색을 했을때 페이징 기능을 호출하긴 하는가만 보면 됨 */

        // When & Then
        mvc.perform(
                get("/articles")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));

        then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());

    }




    /**2) 게시글 (상세) 페이지*/
    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    public void articlesOne() throws Exception {

        Long articleId = 1L;

        long totalCount = 1L; /* Long은 null 가능, long은 불가*/

        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        given(articleService.getArticleCount()).willReturn(totalCount);

        mvc.perform(get("/articles/1")) /** 테스트니까 그냥 1번 글 가져와라 할거임 */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"))
               .andExpect(model().attributeExists("totalCount"));

        then(articleService).should().getArticle(articleId);
        then(articleService).should().getArticleCount();
    }


//    /**3) 게시판 검색 전용*/
//    @Disabled("구현중")
//    @Test
//    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출")
//    public void articlesSearch() throws Exception {
//        mvc.perform(get("/articles/search"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/search"));
//    }
//


    ///////////////////////////////////////
    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "bitstudy",
                LocalDateTime.now(),
                "bitstudy"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "bitstudy",
                "password",
                "bitstudy@email.com",
                "bitstudy",
                "memo memmo",
                LocalDateTime.now(),
                "bitstudy",
                LocalDateTime.now(),
                "bitstudy"
        );
    }

}

















