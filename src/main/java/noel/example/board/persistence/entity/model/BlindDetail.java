package noel.example.board.persistence.entity.model;

import java.time.LocalDateTime;

public record BlindDetail(
        Long adminNo,
        LocalDateTime blindedAt
) {
    public static final BlindDetail DEFAULT = new BlindDetail(null, null);
}
