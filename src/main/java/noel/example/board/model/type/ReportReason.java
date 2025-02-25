package noel.example.board.model.type;

public enum ReportReason {

    ABUSE("욕설"),
    SEXUAL_HARASSMENT("성희롱"),
    ETC("기타 사유")
    ;


    public final String reason;

    ReportReason(String reason) {
        this.reason = reason;
    }

}
