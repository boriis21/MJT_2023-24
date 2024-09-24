package bg.sofia.uni.fmi.mjt.udemy.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
      super(message);
    }
}
