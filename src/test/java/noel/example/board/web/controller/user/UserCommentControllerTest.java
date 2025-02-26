package noel.example.board.web.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.model.type.ReportReason;
import noel.example.board.service.user.UserCommentService;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static noel.example.board.model.constant.TestConstant.USER_NO;
import static noel.example.board.model.constant.TestConstant.USER_NO_DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTestAnnotation
@WebMvcTest(UserCommentController.class)
class UserCommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserCommentService userCommentService;

    private final String BASE_URI = "/v1/user/comment";

    Long commentId = 1L;

    @Test
    @DisplayName("사용자 - 댓글 생성")
    void createComment() throws Exception {
        var commentDto = TestFixture.getCommentDto();

        var userCommentCreateRequest = new UserCommentCreateRequest(null, "text");
        var request = objectMapper.writeValueAsString(userCommentCreateRequest);

        when(userCommentService.createComment(any(UserCommentCreateRequest.class), anyLong()))
                .thenReturn(commentDto);

        mockMvc.perform(post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
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
    @DisplayName("사용자 - 댓글 수정")
    void updateComment() throws Exception {

        var commentDto = TestFixture.getCommentDto();

        var userCommentUpdateRequest = new UsersCommentUpdateRequest(null, "text");
        var request = objectMapper.writeValueAsString(userCommentUpdateRequest);

        when(userCommentService.updateComment(anyLong(), any(UsersCommentUpdateRequest.class), anyLong()))
                .thenReturn(commentDto);

        mockMvc.perform(put(BASE_URI + "/{commentId}", commentId)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
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
    @DisplayName("사용자 - 댓글 삭제(soft)")
    void deleteComment() throws Exception {

        doNothing().when(userCommentService).deleteComment(anyLong(), anyLong());

        mockMvc.perform(delete(BASE_URI + "/{commentId}", commentId)
                        .contentType(APPLICATION_JSON)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
                        )
                ));
    }

    @Test
    @DisplayName("사용자 - 댓글 신고")
    void reportComment() throws Exception {

        var userCommentReportRequest = new UserCommentReportRequest(ReportReason.ETC);
        var request = objectMapper.writeValueAsString(userCommentReportRequest);

        doNothing().when(userCommentService).reportComment(anyLong(), any(UserCommentReportRequest.class), anyLong());

        mockMvc.perform(post(BASE_URI + "/report/{commentId}", commentId)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-report",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("reason").type(STRING).description("댓글/답글 신고 사유")
                        )
                ));

    }

    @Test
    @DisplayName("사용자 - 댓글 공감")
    void likeComment() throws Exception {

        doNothing().when(userCommentService).likeComment(anyLong(), anyLong());

        mockMvc.perform(post(BASE_URI + "/like/{commentId}", commentId)
                        .contentType(APPLICATION_JSON)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-like",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
                        )
                ));

    }

    @Test
    @DisplayName("사용자 - 댓글 공감해제")
    void unlikeComment() throws Exception {

        doNothing().when(userCommentService).unlikeComment(anyLong(), anyLong());

        mockMvc.perform(put(BASE_URI + "/unlike/{commentId}", commentId)
                        .contentType(APPLICATION_JSON)
                        .header(USER_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-unlike",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(USER_NO).description(USER_NO_DESCRIPTION)
                        )
                ));

    }
}