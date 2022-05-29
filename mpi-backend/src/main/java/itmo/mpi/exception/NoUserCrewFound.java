package itmo.mpi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserCrewFound extends RuntimeException {
    public NoUserCrewFound() {
        super("No crew found for current user");
    }
}
