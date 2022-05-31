package itmo.mpi.exception;

public class UserAlreadyHasShipException extends RuntimeException {
    public UserAlreadyHasShipException() {
        super("User already has a ship. Delete it before register new one");
    }
}
