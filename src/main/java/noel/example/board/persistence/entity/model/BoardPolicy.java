package noel.example.board.persistence.entity.model;

import lombok.Builder;
import noel.example.board.model.dto.BoardPolicyDto;

/**
 * @param isReplyEnabled   - 답글 허용 여부
 * @param isCommentEnabled - 댓글 허용 여부
 * @param commentPolicy    - 댓글 정책
 */
@Builder
public record BoardPolicy(
        boolean isReplyEnabled,
        boolean isCommentEnabled,
        CommentPolicy commentPolicy
) {

    /**
     * @param hasPerCommentLimit - 1인 댓글 최대수 지정 여부
     * @param hasDailyUserLimit  - 1일 1인 댓글 최대 수 지정 여부
     * @param maxPerComment      - 1인 댓글 최대수
     * @param maxDailyPerUser    - 1일 1인 댓글 최대 수
     */
    public record CommentPolicy(
            boolean hasPerCommentLimit,
            boolean hasDailyUserLimit,
            int maxPerComment,
            int maxDailyPerUser
    ) {

        public CommentPolicy(BoardPolicyDto.CommentPolicyDto policy) {
            this(
                    policy.hasPerCommentLimit(),
                    policy.hasDailyUserLimit(),
                    policy.maxPerComment(),
                    policy.maxDailyPerUser()
            );
        }
    }
}
