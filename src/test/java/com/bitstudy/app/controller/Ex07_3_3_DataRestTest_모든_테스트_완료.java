package com.bitstudy.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 통합테스트로 변경해서 테스트 할거임
 *
 * Ex07_3_2 에서 articleCommentAllByArticle만 추가됨.
 * */


@SpringBootTest /* 이것만 있으면 MockMvc 를 알아볼 수가 없어서 @AutoConfigureMockMvc 도 같이 넣기 */
@AutoConfigureMockMvc
@DisplayName("Data REST - API 테스트")
@Transactional /* 테스트 돌리면 Hibernate 부분에 select 쿼리문이 나오면서 실제 DB 를 건드리는데, 테스트 끝난 이후에 DB를 롤백 시키는 용도 */

public class Ex07_3_3_DataRestTest_모든_테스트_완료 {
   private final MockMvc mvc; /* 1) MockMvc 생성 (빈 준비)*/

   public Ex07_3_3_DataRestTest_모든_테스트_완료(@Autowired MockMvc mvc) { /* 2) MockMvc 에게 요청에 대한 정보를 입력(주입) */
       this.mvc = mvc;
   }

   /*  [api] - 게시글 리스트 전체 조회 */
   @DisplayName("[api] - 게시글 리스트 전체 조회")
   @Test
   void articleAll() throws Exception {

           mvc.perform(get("/api/articles"))
                   .andExpect(status().isOk())
                   .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
   }
    /*  [api] - 게시글 단건 조회 */
    @DisplayName("[api] - 게시글 단건 조회")
    @Test
    void articleOne() throws Exception {

        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }


    //////////////////////////////////////////
    @DisplayName("[api] - 댓글 리스트 전체 조회")
    @Test
    void articleCommentAll() throws Exception {

        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }


    @DisplayName("[api] - 댓글 단건 조회")
    @Test
    void articleCommentOne() throws Exception {

        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }


    @DisplayName("[api] - 게시글의 댓글 리스트 조회")
    @Test
    void articleCommentAllByArticle() throws Exception {

        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
}










