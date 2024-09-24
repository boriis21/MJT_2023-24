package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;

import java.util.Arrays;

public class BusinessAccount extends AccountBase {
    private final Category[] allowedCategories;

    public BusinessAccount(String username, double balance, Category[] allowedCategories) {
        super(username, balance);
        this.allowedCategories = Arrays.copyOf(allowedCategories, allowedCategories.length);
    }

    @Override
    protected double applyDiscount(Course course) {
        if (!containsCategory(course)) {
            throw new IllegalArgumentException("Cannot purchase a course from the category " + course.getCategory());
        }

        return course.getPrice() * (1 - AccountType.BUSINESS.getDiscount());
    }

    private boolean containsCategory(Course course) {
        for (Category category : allowedCategories) {
            if (category.equals(course.getCategory())) {
                return true;
            }
        }

        return false;
    }
}
