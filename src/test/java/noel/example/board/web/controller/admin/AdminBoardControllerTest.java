package noel.example.board.web.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.model.BoardStatus;
import noel.example.board.service.admin.AdminBoardService;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

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
@WebMvcTest(AdminBoardController.class)
class AdminBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AdminBoardService adminBoardService;

    private String BASE_URI = "/v1/admin/board";

    @Test
    @DisplayName("관리자 - 게시판 생성")
    void createBoard() throws Exception {

        var now = LocalDateTime.now();
        var adminBoardCreateRequest = new AdminBoardCreateRequest(
                "title",
                TestFixture.getBoardPolicyDto(),
                now,
                now,
                BoardStatus.ENABLED
        );

        var request = objectMapper.writeValueAsString(adminBoardCreateRequest);

        var adminBoardDto = TestFixture.getAdminBoardDto();

        when(adminBoardService.createBoard(any(AdminBoardCreateRequest.class), anyLong())).thenReturn(adminBoardDto);

        mockMvc.perform(post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header("X_ADMIN_NO", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("board-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("X_ADMIN_NO").description("admin header")
                        ),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("이름"),
                                fieldWithPath("policy").type(OBJECT).description("댓글/답글 정책"),
                                fieldWithPath("policy.isReplyEnabled").type(BOOLEAN).description("댓글/답글 정책 - 답글 허용 여부"),
                                fieldWithPath("policy.isCommentEnabled").type(BOOLEAN).description("댓글/답글 정책 - 댓글 허용 여부"),
                                fieldWithPath("policy.commentPolicy").type(OBJECT).description("댓글/답글 정책 - 댓글 정책"),
                                fieldWithPath("policy.commentPolicy.hasPerCommentLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.hasDailyUserLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.maxPerComment").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수"),
                                fieldWithPath("policy.commentPolicy.maxDailyPerUser").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수"),
                                fieldWithPath("startedAt").type(STRING).description("시작일"),
                                fieldWithPath("endedAt").type(STRING).description("종료일, null 인 경우 종료일 없음"),
                                fieldWithPath("status").type(STRING).description("활성화 상태")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("title").type(STRING).description("이름"),
                                fieldWithPath("policy").type(OBJECT).description("댓글/답글 정책"),
                                fieldWithPath("policy.isReplyEnabled").type(BOOLEAN).description("댓글/답글 정책 - 답글 허용 여부"),
                                fieldWithPath("policy.isCommentEnabled").type(BOOLEAN).description("댓글/답글 정책 - 댓글 허용 여부"),
                                fieldWithPath("policy.commentPolicy").type(OBJECT).description("댓글/답글 정책 - 댓글 정책"),
                                fieldWithPath("policy.commentPolicy.hasPerCommentLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.hasDailyUserLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.maxPerComment").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수"),
                                fieldWithPath("policy.commentPolicy.maxDailyPerUser").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수"),
                                fieldWithPath("startedAt").type(STRING).description("시작일"),
                                fieldWithPath("endedAt").type(STRING).description("종료일, null 인 경우 종료일 없음"),
                                fieldWithPath("status").type(STRING).description("활성화 상태"),
                                fieldWithPath("createdAt").type(STRING).description("생성일"),
                                fieldWithPath("updatedAt").type(STRING).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("updatedBy").type(STRING).description("수정자")
                        )
                ));

    }

    @Test
    @DisplayName("관리자 - 게시판 수정")
    void updateBoard() throws Exception {
        var now = LocalDateTime.now();

        var adminBoardUpdateRequest = new AdminBoardUpdateRequest(
                "",
                TestFixture.getBoardPolicyDto(),
                now,
                now,
                BoardStatus.ENABLED
        );

        var request = objectMapper.writeValueAsString(adminBoardUpdateRequest);

        var adminBoardDto = TestFixture.getAdminBoardDto();

        when(adminBoardService.updateBoard(anyLong(), any(AdminBoardUpdateRequest.class), anyLong())).thenReturn(adminBoardDto);

        mockMvc.perform(put(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header("X_ADMIN_NO", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("board-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("X_ADMIN_NO").description("admin header")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("이름"),
                                fieldWithPath("policy").type(OBJECT).description("댓글/답글 정책"),
                                fieldWithPath("policy.isReplyEnabled").type(BOOLEAN).description("댓글/답글 정책 - 답글 허용 여부"),
                                fieldWithPath("policy.isCommentEnabled").type(BOOLEAN).description("댓글/답글 정책 - 댓글 허용 여부"),
                                fieldWithPath("policy.commentPolicy").type(OBJECT).description("댓글/답글 정책 - 댓글 정책"),
                                fieldWithPath("policy.commentPolicy.hasPerCommentLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.hasDailyUserLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.maxPerComment").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수"),
                                fieldWithPath("policy.commentPolicy.maxDailyPerUser").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수"),
                                fieldWithPath("startedAt").type(STRING).description("시작일"),
                                fieldWithPath("endedAt").type(STRING).description("종료일, null 인 경우 종료일 없음"),
                                fieldWithPath("status").type(STRING).description("활성화 상태")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("title").type(STRING).description("이름"),
                                fieldWithPath("policy").type(OBJECT).description("댓글/답글 정책"),
                                fieldWithPath("policy.isReplyEnabled").type(BOOLEAN).description("댓글/답글 정책 - 답글 허용 여부"),
                                fieldWithPath("policy.isCommentEnabled").type(BOOLEAN).description("댓글/답글 정책 - 댓글 허용 여부"),
                                fieldWithPath("policy.commentPolicy").type(OBJECT).description("댓글/답글 정책 - 댓글 정책"),
                                fieldWithPath("policy.commentPolicy.hasPerCommentLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.hasDailyUserLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수 지정 여부"),
                                fieldWithPath("policy.commentPolicy.maxPerComment").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수"),
                                fieldWithPath("policy.commentPolicy.maxDailyPerUser").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수"),
                                fieldWithPath("startedAt").type(STRING).description("시작일"),
                                fieldWithPath("endedAt").type(STRING).description("종료일, null 인 경우 종료일 없음"),
                                fieldWithPath("status").type(STRING).description("활성화 상태"),
                                fieldWithPath("createdAt").type(STRING).description("생성일"),
                                fieldWithPath("updatedAt").type(STRING).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("updatedBy").type(STRING).description("수정자")
                        )
                ));

    }

    @Test
    @DisplayName("관리자 - 게시판 미사용 상태로 수정")
    void deleteBoard() throws Exception {
        doNothing().when(adminBoardService).deleteBoard(anyLong(), anyLong());

        mockMvc.perform(delete(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header("X_ADMIN_NO", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("board-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("X_ADMIN_NO").description("admin header")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        )
                ));
    }
}
