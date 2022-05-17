package itmo.mpi.exception;


import static java.text.MessageFormat.format;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String nick) {
        super(format("User {0} already exist.", nick));
    }
}
