package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotCompletedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

import java.util.Arrays;

public abstract class AccountBase implements Account {
    private static final int MAX_CAPACITY = 100;
    private static final double MIN_GRADE = 2.00;
    private static final double MAX_GRADE = 6.00;
    private final String username;
    private double balance;
    protected Course[] courses;
    private int courseCounter;

    public AccountBase(String username, double balance) {
        this(username, balance, new Course[MAX_CAPACITY], 0);
    }

    AccountBase(String username, double balance, Course[] courses, int courseCounter) {
        this.username = username;
        this.balance = balance;
        this.courses = Arrays.copyOf(courses, MAX_CAPACITY);
        this.courseCounter = courseCounter;
    }

    public String getUsername() {
        return username;
    }

    public void addToBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Trying to add negative amount of money");
        }

        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (courseCounter == MAX_CAPACITY) {
            throw new MaxCourseCapacityReachedException(username + " has reached the limit for courses");
        }

        if (isCoursePurchased(course)) {
            throw new CourseAlreadyPurchasedException(username + " already owns " + course.getName());
        }

        if (balance - course.getPrice() < 0) {
            throw new InsufficientBalanceException(username + " does not have enough money to buy " + course.getName());
        }

        balance -= applyDiscount(course);
        Course toAdd = new Course(course);
        courses[courseCounter++] = toAdd;
        toAdd.purchase();
    }

    protected abstract double applyDiscount(Course course);

    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete) throws CourseNotPurchasedException, ResourceNotFoundException {
        if (!isCoursePurchased(course)) {
            throw new CourseNotPurchasedException("The course %s was not purchased by %s ".formatted(course.getName(), username));
        }

        for (Resource resource : resourcesToComplete) {
            course.completeResource(resource);
        }
    }

    private boolean isCoursePurchased(Course course) {
        return getCourse(course) != null;
    }

    private Course getCourse(Course course) {
        for (int i = 0; i < courseCounter; i++) {
            if (courses[i].equals(course)) {
                return courses[i];
            }
        }

        return null;
    }

    @Override
    public void completeCourse(Course course, double grade) throws CourseNotPurchasedException, CourseNotCompletedException {
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new IllegalArgumentException("The grade for the course completion is invalid. It should be between 2.0 and 6.0");
        }

        Course toComplete = getCourse(course);

        if (toComplete == null) {
            throw new CourseNotPurchasedException("The course %s was not purchased by %s".formatted(course.getName(), username));
        }

        if (!toComplete.isCompleted()) {
            throw new CourseNotCompletedException("The course %s is not completed by %s".formatted(course.getName(), username));
        }
    }

    public Course getLeastCompletedCourse() {
        if (courseCounter == 0) {
            return null;
        }

        Course leastCompleted = courses[0];
        for (int i = 0; i < courseCounter; i++) {
            if (courses[i].getCompletionPercentage() < leastCompleted.getCompletionPercentage()) {
                leastCompleted = courses[i];
            }
        }

        return leastCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof AccountBase account) {
            return account.username.equals(this.username);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
