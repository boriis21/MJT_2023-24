package bg.sofia.uni.fmi.mjt.udemy.course.duration;

public record ResourceDuration(int minutes) {
    public ResourceDuration {
        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("Resource duration have to be between 60 and 0");
        }
    }
}
