package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseAlreadyPurchasedException extends RuntimeException {
    public CourseAlreadyPurchasedException(String message) {
        super(message);
    }
}
