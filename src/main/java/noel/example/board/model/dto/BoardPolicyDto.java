package noel.example.board.model.dto;

import noel.example.board.persistence.entity.model.BoardPolicy;

/**
 * @param isReplyEnabled - 답글 허용 여부
 * @param isCommentEnabled - 댓글 허용 여부
 * @param commentPolicy - 댓글 정책
 * */
public record BoardPolicyDto(
        boolean isReplyEnabled,
        boolean isCommentEnabled,
        CommentPolicyDto commentPolicy
) {

    /**
     * @param hasPerCommentLimit - 1인 댓글 최대수 지정 여부
     * @param hasDailyUserLimit - 1일 1인 댓글 최대 수 지정 여부
     * @param maxPerComment - 1인 댓글 최대수
     * @param maxDailyPerUser - 1일 1인 댓글 최대 수
     * */
    public record CommentPolicyDto(
            boolean hasPerCommentLimit,
            boolean hasDailyUserLimit,
            int maxPerComment,
            int maxDailyPerUser
    ) {
        public CommentPolicyDto(BoardPolicy.CommentPolicy commentPolicy) {
            this(
                    commentPolicy.hasPerCommentLimit(),
                    commentPolicy.hasDailyUserLimit(),
                    commentPolicy.maxPerComment(),
                    commentPolicy.maxDailyPerUser()
            );
        }
    }

    public BoardPolicyDto(BoardPolicy boardPolicy) {
        this(
                boardPolicy.isReplyEnabled(),
                boardPolicy.isCommentEnabled(),
                new CommentPolicyDto(boardPolicy.commentPolicy())
        );
    }
}
