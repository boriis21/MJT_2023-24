package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.udemy.account.AccountBase;
import bg.sofia.uni.fmi.mjt.udemy.account.BusinessAccount;
import bg.sofia.uni.fmi.mjt.udemy.account.EducationalAccount;
import bg.sofia.uni.fmi.mjt.udemy.account.StandardAccount;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;

public class Main {
    public static void main(String[] args) {
        // Създаване на ресурси за курсовете
        Resource resource1 = new Resource("Intro to Java", new ResourceDuration(45));
        Resource resource2 = new Resource("Advanced Java", new ResourceDuration(60));
        Resource resource3 = new Resource("Marketing Basics", new ResourceDuration(30));
        Resource resource4 = new Resource("Python for Data Science", new ResourceDuration(55));
        Resource resource5 = new Resource("Business Strategies", new ResourceDuration(50));
        Resource[] javaResources = {resource1, resource2};
        Resource[] businessResources = {resource5};
        Resource[] marketingResources = {resource3};
        Resource[] pythonResources = {resource4};

        // Създаване на курсове
        Course javaCourse = new Course("Java Programming", "Learn Java from scratch", 50.0, javaResources, Category.SOFTWARE_ENGINEERING);
        Course businessCourse = new Course("Business Basics", "Introduction to business concepts", 40.0, businessResources, Category.BUSINESS);
        Course marketingCourse = new Course("Marketing 101", "Basics of digital marketing", 25.0, marketingResources, Category.MARKETING);
        Course pythonCourse = new Course("Python for Data Science", "Learn Python for data analysis", 60.0, pythonResources, Category.SOFTWARE_ENGINEERING);
        Course financeCourse = new Course("Finance Fundamentals", "Intro to personal finance", 35.0, marketingResources, Category.FINANCE);
        Course musicCourse = new Course("Music Theory", "Learn the basics of music theory", 20.0, marketingResources, Category.MUSIC);
        Course designCourse = new Course("Design Principles", "Learn the fundamentals of design", 45.0, marketingResources, Category.DESIGN);

        // Добавяне на курсове в масив
        Course[] courses = {javaCourse, businessCourse, marketingCourse, pythonCourse, financeCourse, musicCourse, designCourse};

        // Създаване на акаунти
        AccountBase standardAccount = new StandardAccount("john_doe", 100.0);
        AccountBase educationalAccount = new EducationalAccount("jane_smith", 300.0);
        AccountBase businessAccount = new BusinessAccount("company_inc", 500.0, new Category[]{Category.BUSINESS, Category.FINANCE});

        // Създаване на платформата Udemy
        Udemy udemy = new Udemy(new AccountBase[]{standardAccount, educationalAccount, businessAccount}, courses);

        // Търсене на курс по ключова дума
        System.out.println("Courses with keyword 'Python':");
        Course[] pythonCourses = udemy.findByKeyword("Python");
        for (Course course : pythonCourses) {
            System.out.println(course.getName() + " - Price: " + course.getPrice());
        }

        System.out.println("\nCourses with keyword 'Basics':");
        Course[] basicCourses = udemy.findByKeyword("Basics");
        for (Course course : basicCourses) {
            System.out.println(course.getName() + " - Price: " + course.getPrice());
        }

        educationalAccount.buyCourse(javaCourse);
        educationalAccount.completeResourcesFromCourse(javaCourse, javaResources);
        educationalAccount.completeCourse(javaCourse, 4.5);
        educationalAccount.buyCourse(financeCourse);
        educationalAccount.completeResourcesFromCourse(financeCourse, marketingResources);
        educationalAccount.completeCourse(financeCourse, 5);
        educationalAccount.buyCourse(pythonCourse);
        educationalAccount.completeResourcesFromCourse(pythonCourse, pythonResources);
        educationalAccount.completeCourse(pythonCourse, 6);
        educationalAccount.buyCourse(designCourse);
        educationalAccount.completeResourcesFromCourse(designCourse, marketingResources);
        educationalAccount.completeCourse(designCourse, 4);
        educationalAccount.buyCourse(businessCourse);
        educationalAccount.completeResourcesFromCourse(businessCourse, businessResources);
        educationalAccount.completeCourse(businessCourse, 5.5);
        educationalAccount.buyCourse(musicCourse);
        businessAccount.buyCourse(businessCourse);
        businessAccount.buyCourse(financeCourse);
        standardAccount.buyCourse(musicCourse);

        // Търсене на акаунт по име
        try {
            Account foundAccount = udemy.getAccount("jane_smith");
            System.out.println("\nAccount found: " + foundAccount.getUsername());
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found");
        }

        // Списък на курсове в дадена категория
        System.out.println("\nCourses in category 'SOFTWARE_ENGINEERING':");
        Course[] softwareCourses = udemy.getAllCoursesByCategory(Category.SOFTWARE_ENGINEERING);
        for (Course course : softwareCourses) {
            System.out.println(course.getName() + " - Price: " + course.getPrice());
        }

        // Най-дълъг курс
        Course longestCourse = udemy.getLongestCourse();
        System.out.println("\nLongest course on the platform: " + longestCourse.getName() + " with duration: " + longestCourse.getTotalTime().hours() + "h " + longestCourse.getTotalTime().minutes() + "m");

        // Най-евтин курс в категория SOFTWARE_ENGINEERING
        Course cheapestSoftwareCourse = udemy.getCheapestByCategory(Category.SOFTWARE_ENGINEERING);
        System.out.println("\nCheapest software engineering course: " + cheapestSoftwareCourse.getName() + " at price: " + cheapestSoftwareCourse.getPrice());

        // Баланс на акаунтите след покупките
        System.out.println("\nJohn's balance after purchase: " + standardAccount.getBalance());
        System.out.println("Jane's balance after purchase: " + educationalAccount.getBalance());
        System.out.println("Company's balance after purchase: " + businessAccount.getBalance());
    }
}
