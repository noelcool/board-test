package noel.example.board.web.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import noel.example.board.config.ControllerTestAnnotation;
import noel.example.board.fixture.TestFixture;
import noel.example.board.model.type.BoardStatus;
import noel.example.board.service.admin.AdminBoardService;
import noel.example.board.service.admin.AdminCommentService;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardSearchRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
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

    @MockitoBean
    AdminCommentService adminCommentService;

    private final String BASE_URI = "/v1/admin/board";

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

        when(adminBoardService.createBoard(any(AdminBoardCreateRequest.class), anyLong()))
                .thenReturn(adminBoardDto);

        mockMvc.perform(post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
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

        when(adminBoardService.updateBoard(anyLong(), any(AdminBoardUpdateRequest.class), anyLong()))
                .thenReturn(adminBoardDto);

        mockMvc.perform(put(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
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
    void disableBoard() throws Exception {
        doNothing().when(adminBoardService).disableBoard(anyLong(), anyLong());

        mockMvc.perform(delete(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-disable",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("관리자 - 게시판 1개 조회")
    void findBoard() throws Exception {

        var adminBoardDto = TestFixture.getAdminBoardDto();

        when(adminBoardService.findBoard(anyLong())).thenReturn(adminBoardDto);

        mockMvc.perform(get(BASE_URI + "/{boardId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-find-one",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
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
    @DisplayName("관리자 - 게시판 1개의 댓글 목록 조회")
    void findComments() throws Exception {

        var adminCommentDtos = List.of(TestFixture.getCommentDto());

        when(adminCommentService.findCommentsByBoardId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(adminCommentDtos, PageRequest.of(1, 10), adminCommentDtos.size()));

        mockMvc.perform(get(BASE_URI + "/{boardId}/comments", 1L)
                        .contentType(APPLICATION_JSON)
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "id.desc")
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-comments",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        ),
                        responseFields(
                                beneathPath("data"),
                                fieldWithPath("content[].id").type(NUMBER).description("아이디"),
                                fieldWithPath("content[].parentId").type(NUMBER).description("부모 댓글 아이디").optional(),
                                fieldWithPath("content[].text").type(STRING).description("이름"),
                                fieldWithPath("content[].createdAt").type(STRING).description("생성일"),
                                fieldWithPath("content[].createdBy").type(STRING).description("생성자"),
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
    @DisplayName("관리자 - 게시판 목록 조회")
    void searchBoard() throws Exception {
        var adminBoardDtos = List.of(TestFixture.getAdminBoardDto());

        when(adminBoardService.searchBoard(any(AdminBoardSearchRequest.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(adminBoardDtos, PageRequest.of(1, 10), adminBoardDtos.size()));

        var now = LocalDateTime.now().toString();
        mockMvc.perform(get(BASE_URI + "/list")
                        .contentType(APPLICATION_JSON)
                        .queryParam("status", BoardStatus.ENABLED.toString())
                        .queryParam("startedAt", now)
                        .queryParam("endedAt", now)
                        .queryParam("title", "제목")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "id.desc")
                        .header(ADMIN_NO, 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("admin-board-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ADMIN_NO).description(ADMIN_NO_DESCRIPTION)
                        ),
                        queryParameters(
                                parameterWithName("status").description("게시판 상태"),
                                parameterWithName("startedAt").description("시작일 (YYYY-MM-DD)"),
                                parameterWithName("endedAt").description("종료일 (YYYY-MM-DD)"),
                                parameterWithName("title").description("제목"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"),
                                parameterWithName("sort").description("정렬")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("content[].id").type(NUMBER).description("아이디"),
                                fieldWithPath("content[].title").type(STRING).description("이름"),
                                fieldWithPath("content[].policy").type(OBJECT).description("댓글/답글 정책"),
                                fieldWithPath("content[].policy.isReplyEnabled").type(BOOLEAN).description("댓글/답글 정책 - 답글 허용 여부"),
                                fieldWithPath("content[].policy.isCommentEnabled").type(BOOLEAN).description("댓글/답글 정책 - 댓글 허용 여부"),
                                fieldWithPath("content[].policy.commentPolicy").type(OBJECT).description("댓글/답글 정책 - 댓글 정책"),
                                fieldWithPath("content[].policy.commentPolicy.hasPerCommentLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수 지정 여부"),
                                fieldWithPath("content[].policy.commentPolicy.hasDailyUserLimit").type(BOOLEAN).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수 지정 여부"),
                                fieldWithPath("content[].policy.commentPolicy.maxPerComment").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1인 댓글 최대수"),
                                fieldWithPath("content[].policy.commentPolicy.maxDailyPerUser").type(NUMBER).description("댓글/답글 정책 - 댓글 정책 - 1일 1인 댓글 최대 수"),
                                fieldWithPath("content[].startedAt").type(STRING).description("시작일"),
                                fieldWithPath("content[].endedAt").type(STRING).description("종료일, null 인 경우 종료일 없음"),
                                fieldWithPath("content[].status").type(STRING).description("활성화 상태"),
                                fieldWithPath("content[].createdAt").type(STRING).description("생성일"),
                                fieldWithPath("content[].updatedAt").type(STRING).description("수정일"),
                                fieldWithPath("content[].createdBy").type(STRING).description("생성자"),
                                fieldWithPath("content[].updatedBy").type(STRING).description("수정자"),
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
