package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotCompletedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;

public class EducationalAccount extends AccountBase {
    private static final int GRADES_MAX_CAPACITY = 5;
    private static final double MIN_GRADE_DISCOUNT = 4.5;
    private double[] grades;
    private int gradesCount;
    private boolean canGetDiscount;

    public EducationalAccount(String username, double balance) {
        super(username, balance);
        this.grades = new double[GRADES_MAX_CAPACITY];
        this.gradesCount = 0;
        this.canGetDiscount = false;
    }

    @Override
    protected double applyDiscount(Course course) {
        if (!canGetDiscount) {
            return course.getPrice();
        }

        canGetDiscount = false;
        return course.getPrice() * (1 - AccountType.EDUCATION.getDiscount());
    }

    @Override
    public void completeCourse(Course course, double grade) throws CourseNotPurchasedException, CourseNotCompletedException {
        super.completeCourse(course, grade);

        this.grades[gradesCount++] = grade;

        if (gradesCount == GRADES_MAX_CAPACITY) {
            if (getAverageGrade() > MIN_GRADE_DISCOUNT) {
                canGetDiscount = true;
            }

            gradesCount = 0;
        }
    }

    private double getAverageGrade() {
        double grade = 0.0;
        for (int i = 0; i < gradesCount; i++) {
            grade += grades[i];
        }

        return grade / gradesCount;
    }
}
