package bg.sofia.uni.fmi.mjt.udemy.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
      super(message);
    }
}
