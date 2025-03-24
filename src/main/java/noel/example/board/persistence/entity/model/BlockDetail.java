package noel.example.board.persistence.entity.model;

import java.time.LocalDateTime;

public record BlockDetail(
        Long adminNo,
        String adminName,
        LocalDateTime blindedAt
) {
    public static final BlockDetail DEFAULT = new BlockDetail(null, null,null);
}
