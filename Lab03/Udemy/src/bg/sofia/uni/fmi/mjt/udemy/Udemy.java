package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.AccountBase;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

import java.util.Arrays;

public class Udemy implements LearningPlatform {
    private AccountBase[] accounts;
    private Course[] courses;

    public Udemy(AccountBase[] accounts, Course[] courses) {
        this.accounts = Arrays.copyOf(accounts, accounts.length);
        this.courses = Arrays.copyOf(courses, courses.length);
    }

    @Override
    public Course findByName(String name) throws CourseNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name you are trying to find a course by is null or blank");
        }

        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }

        throw new CourseNotFoundException("The course %s is not in the system".formatted(name));
    }

    @Override
    public Course[] findByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("The keyword you are trying to find a course by is null or blank");
        }

        Course[] coursesByKeyword = new Course[courses.length];
        int counter = 0;

        for (Course course : courses) {
            if (course.getName().contains(keyword) || course.getDescription().contains(keyword)) {
                coursesByKeyword[counter++] = course;
            }
        }

        return Arrays.copyOf(coursesByKeyword, counter);
    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("The category you are trying to find courses by is null");
        }

        Course[] coursesByCategory = new Course[courses.length];
        int counter = 0;

        for (Course course : courses) {
            if (course.getCategory().equals(category)) {
                coursesByCategory[counter++] = course;
            }
        }

        return Arrays.copyOf(coursesByCategory, counter);
    }

    public AccountBase getAccount(String name) throws AccountNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name you are trying to find an account by is null or blank");
        }

        for (AccountBase acc : accounts) {
            if (acc.getUsername().equals(name)) {
                return acc;
            }
        }

        throw new AccountNotFoundException("Account with username %s was not found".formatted(name));
    }

    public Course getLongestCourse() {
        if (courses.length == 0) {
            return null;
        }

        Course longestCourse = courses[0];
        for (Course course : courses) {
            if (course.durationIntoMinutes(course.getTotalTime()) > longestCourse.durationIntoMinutes(longestCourse.getTotalTime())) {
                longestCourse = course;
            }
        }

        return longestCourse;
    }

    public Course getCheapestByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("The category you are trying to find courses by is null");
        }

        Course[] coursesFromCategory = getAllCoursesByCategory(category);
        Course cheapest = coursesFromCategory[0];

        for (Course course : coursesFromCategory) {
            if (course.getPrice() < cheapest.getPrice()) {
                cheapest = course;
            }
        }

        return cheapest;
    }
}