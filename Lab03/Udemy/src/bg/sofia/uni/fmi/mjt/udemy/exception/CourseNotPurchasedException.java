package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseNotPurchasedException extends RuntimeException {
    public CourseNotPurchasedException(String message) {
        super(message);
    }
}
