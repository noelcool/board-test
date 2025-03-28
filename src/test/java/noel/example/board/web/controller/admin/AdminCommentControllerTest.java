package noel.example.board.web.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.service.admin.AdminCommentService;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;

import static noel.example.board.model.constant.TestConstant.ADMIN_NO;
import static noel.example.board.model.constant.TestConstant.ADMIN_NO_DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ControllerTestAnnotation
@WebMvcTest(AdminCommentController.class)
class AdminCommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AdminCommentService adminCommentService;

    private final String BASE_URI = "/v1/admin/comment";

    @Test
    @DisplayName("관리자 - 댓글/답글 단건 조회")
    void findComment() throws Exception {

        var commentDto = TestFixture.getCommentDto();
        when(adminCommentService.findComment(anyLong()))
                .thenReturn(commentDto);

        mockMvc.perform(get(BASE_URI + "/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-find-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글/답글 아이디")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("blindAdminNo").type(NUMBER).description("댓글/답글 차단 관리자 번호"),
                                fieldWithPath("blindedAt").type(STRING).description("댓글/답글 차단 일시"),
                                fieldWithPath("isAuthor").type(BOOLEAN).description("댓글/답글 작성자 여부"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("createdAt").type(STRING).description("생성일시"),
                                fieldWithPath("updatedAt").type(STRING).description("수정일시"),
                                fieldWithPath("isLike").type(BOOLEAN).description("댓글/답글 공감 여부")
                        )
                ));

    }

    @Test
    @DisplayName("관리자 - 댓글 하나의 답글 목록 조회")
    void findReplies() throws Exception {

        var commentDtos = List.of(TestFixture.getCommentDto());
        when(adminCommentService.findReplies(anyLong(), any()))
                .thenReturn(new PageImpl<>(commentDtos, PageRequest.of(1, 10), commentDtos.size()));

        mockMvc.perform(get(BASE_URI + "/{commentId}/replies", 1L)
                        .contentType(APPLICATION_JSON)
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "id.desc")
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-find-replies",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글/답글 아이디")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("content[].id").type(NUMBER).description("아이디"),
                                fieldWithPath("content[].parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("content[].text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("content[].blindAdminNo").type(NUMBER).description("댓글/답글 차단 관리자 번호"),
                                fieldWithPath("content[].blindedAt").type(STRING).description("댓글/답글 차단 일시"),
                                fieldWithPath("content[].isAuthor").type(BOOLEAN).description("댓글/답글 작성자 여부"),
                                fieldWithPath("content[].createdBy").type(STRING).description("생성자"),
                                fieldWithPath("content[].createdAt").type(STRING).description("생성일시"),
                                fieldWithPath("content[].updatedAt").type(STRING).description("수정일시"),
                                fieldWithPath("content[].isLike").type(BOOLEAN).description("댓글/답글 공감 여부"),
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

    @Test
    @DisplayName("관리자 - 댓글/답글 생성")
    void createComment() throws Exception {
        var adminCommentCreateRequest = new AdminCommentCreateRequest(
                null,
                "text"
        );
        var request = objectMapper.writeValueAsString(adminCommentCreateRequest);

        var commentDto = TestFixture.getCommentDto();

        when(adminCommentService.createComment(anyLong(), any(AdminCommentCreateRequest.class), anyLong()))
                .thenReturn(commentDto);

        mockMvc.perform(post(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        ),
                        requestFields(
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("blindAdminNo").type(NUMBER).description("댓글/답글 차단 관리자 번호"),
                                fieldWithPath("blindedAt").type(STRING).description("댓글/답글 차단 일시"),
                                fieldWithPath("isAuthor").type(BOOLEAN).description("댓글/답글 작성자 여부"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("createdAt").type(STRING).description("생성일시"),
                                fieldWithPath("updatedAt").type(STRING).description("수정일시"),
                                fieldWithPath("isLike").type(BOOLEAN).description("댓글/답글 공감 여부"),
                                fieldWithPath("replies").optional().type(ARRAY).description("답글 목록")
                        )
                ));
    }

    @Test
    @DisplayName("관리자 - 댓글/답글 수정")
    void updateComment() throws Exception {
        var now = LocalDateTime.now();

        var adminCommentUpdateRequest = new AdminCommentUpdateRequest("text");
        var request = objectMapper.writeValueAsString(adminCommentUpdateRequest);

        var commentDto = TestFixture.getCommentDto();

        when(adminCommentService.updateComment(anyLong(), any(), anyLong()))
                .thenReturn(commentDto);

        mockMvc.perform(put(BASE_URI + "/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("blindAdminNo").type(NUMBER).description("댓글/답글 차단 관리자 번호"),
                                fieldWithPath("blindedAt").type(STRING).description("댓글/답글 차단 일시"),
                                fieldWithPath("isAuthor").type(BOOLEAN).description("댓글/답글 작성자 여부"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("createdAt").type(STRING).description("생성일시"),
                                fieldWithPath("updatedAt").type(STRING).description("수정일시"),
                                fieldWithPath("isLike").type(BOOLEAN).description("댓글/답글 공감 여부")
                        )
                ));
    }

    @Test
    @DisplayName("관리자 - 댓글/답글 삭제")
    void deleteComment() throws Exception {
        doNothing().when(adminCommentService).deleteComment(anyLong(), anyLong());

        mockMvc.perform(delete(BASE_URI + "/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글/답글 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("관리자 - 댓글/답글 차단")
    void blockComment() throws Exception {
        doNothing().when(adminCommentService).blockComment(anyLong(), anyLong());

        mockMvc.perform(post(BASE_URI + "/block/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-block",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글/답글 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("관리자 - 댓글/답글 차단 해제")
    void unblockComment() throws Exception {
        doNothing().when(adminCommentService).blockComment(anyLong(), anyLong());

        mockMvc.perform(post(BASE_URI + "/unblock/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-unblock",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글/답글 아이디")
                        )
                ));
    }
}