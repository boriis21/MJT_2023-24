package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseNotCompletedException extends RuntimeException {
    public CourseNotCompletedException(String message) {
        super(message);
    }
}
