package itmo.mpi.exception;

public class UserAlreadyHasCrewException extends RuntimeException {
    public UserAlreadyHasCrewException() {
        super("User already has a crew. Delete it before register new one");
    }
}
