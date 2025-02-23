package noel.example.board.web.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.service.admin.UserCommentService;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                        .header("X_USER_NO", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("user-board-comment-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("X_USER_NO").description("user header")
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
    void updateComment() throws Exception {
    }

    @Test
    void deleteComment() throws Exception {
    }

    @Test
    void reportComment() throws Exception {
    }

    @Test
    void likeBoard() throws Exception {
    }

    @Test
    void unlikeBoard() throws Exception {
    }
}