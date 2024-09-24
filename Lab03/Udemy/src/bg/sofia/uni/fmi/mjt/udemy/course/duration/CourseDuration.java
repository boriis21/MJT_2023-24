package bg.sofia.uni.fmi.mjt.udemy.course.duration;

import bg.sofia.uni.fmi.mjt.udemy.course.Resource;

public record CourseDuration(int hours, int minutes) {
    public CourseDuration {
        if (hours < 0 || hours > 24) {
            throw new IllegalArgumentException("Hours have to be between 24 and 0");
        }

        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("Minutes have to be between 60 and 0");
        }
    }

    public static CourseDuration of(Resource[] content) {
        int totalMinutes = 0;

        for (Resource resource : content) {
            totalMinutes += resource.getDuration().minutes();
        }

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        return new CourseDuration(hours, minutes);
    }
}
