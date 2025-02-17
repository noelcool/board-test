package noel.example.board.model.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private String message;
    private HttpStatus httpStatus;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus httpStatus, T data) {
        this.httpStatus = httpStatus;
        this.data = data;
    }

}
