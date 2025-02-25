package noel.example.board.web.request.user;

import noel.example.board.model.type.ReportReason;

public record UserCommentReportRequest(
        ReportReason reason
) {
}
