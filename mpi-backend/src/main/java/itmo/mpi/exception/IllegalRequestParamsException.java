package itmo.mpi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalRequestParamsException extends RuntimeException {

    public IllegalRequestParamsException(String message) {
        super(message);
    }
}
