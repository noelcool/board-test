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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

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
    @DisplayName("관리자 - 댓글/답글 생성")
    void createComment() throws Exception {
        var adminCommentCreateRequest = new AdminCommentCreateRequest(
                null,
                "text"
        );
        var request = objectMapper.writeValueAsString(adminCommentCreateRequest);

        var commentDto = TestFixture.getCommentDto();

        when(adminCommentService.createComment(any(AdminCommentCreateRequest.class), anyLong()))
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
                        requestFields(
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("parentId").optional().type(NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("text").type(STRING).description("댓글/답글 내용"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("createdAt").type(STRING).description("생성일"),
                                fieldWithPath("updatedAt").optional().type(STRING).description("수정일"),
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
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("createdAt").type(STRING).description("생성일"),
                                fieldWithPath("updatedAt").optional().type(STRING).description("수정일")
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
    void blindComment() throws Exception {
        doNothing().when(adminCommentService).blindComment(anyLong(), anyLong());

        mockMvc.perform(post(BASE_URI + "/blind/{commentId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-comment-blind",
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