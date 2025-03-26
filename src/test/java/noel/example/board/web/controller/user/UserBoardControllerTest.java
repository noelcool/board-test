package noel.example.board.web.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.service.user.UserCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static noel.example.board.model.constant.TestConstant.USER_NO;
import static noel.example.board.model.constant.TestConstant.USER_NO_DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTestAnnotation
@WebMvcTest(UserBoardController.class)
class UserBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserCommentService userCommentService;

    private final String BASE_URI = "/v1/user/board";

    @Test
    @DisplayName("사용자 - 게시판 1개의 댓글 목록 조회")
    void getBoardComments() throws Exception {
        var commentDtos = List.of(TestFixture.getCommentDto());

        when(userCommentService.getBoardComments(anyLong(), any(Pageable.class), anyLong()))
                .thenReturn(new PageImpl<>(commentDtos, PageRequest.of(1, 10), commentDtos.size()));

        mockMvc.perform(get(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "id.desc")
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("content[].id").type(NUMBER).description("아이디"),
                                fieldWithPath("content[].parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("content[].text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("content[].isAuthor").type(BOOLEAN).description("댓글/답글 작성자 여부"),
                                fieldWithPath("content[].createdBy").type(STRING).description("생성자"),
                                fieldWithPath("content[].createdAt").type(STRING).description("생성일시"),
                                fieldWithPath("content[].updatedAt").optional().type(STRING).description("수정일"),
                                fieldWithPath("content[].isLike").optional().type(BOOLEAN).description("댓글 공감 여부"),
                                fieldWithPath("pageable").type(OBJECT).description("pageable"),
                                fieldWithPath("pageable.pageNumber").type(NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("pageable.pageSize").type(NUMBER).description("페이지 크기"),
                                fieldWithPath("pageable.sort").type(OBJECT).description("정렬 정보"),
                                fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("정렬 비어있는지 여부"),
                                fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("정렬되지 않음 여부"),
                                fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬 여부"),
                                fieldWithPath("pageable.offset").type(NUMBER).description("오프셋"),
                                fieldWithPath("pageable.paged").type(BOOLEAN).description("페이징 여부"),
                                fieldWithPath("pageable.unpaged").type(BOOLEAN).description("비페이징 여부"),
                                fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("totalElements").type(NUMBER).description("전체 요소 수"),
                                fieldWithPath("totalPages").type(NUMBER).description("전체 페이지 수"),
                                fieldWithPath("first").type(BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("size").type(NUMBER).description("현재 페이지의 크기"),
                                fieldWithPath("number").type(NUMBER).description("페이지 번호"),
                                fieldWithPath("sort").type(OBJECT).description("정렬 정보"),
                                fieldWithPath("sort.empty").type(BOOLEAN).description("정렬 비어있는지 여부"),
                                fieldWithPath("sort.unsorted").type(BOOLEAN).description("정렬되지 않음 여부"),
                                fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬 여부"),
                                fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지의 요소 수"),
                                fieldWithPath("empty").type(BOOLEAN).description("비어있는 페이지 여부")
                        )
                ));

    }


}