package bg.sofia.uni.fmi.mjt.udemy.exception;

public class MaxCourseCapacityReachedException extends RuntimeException {
    public MaxCourseCapacityReachedException(String message) {
        super(message);
    }
}
