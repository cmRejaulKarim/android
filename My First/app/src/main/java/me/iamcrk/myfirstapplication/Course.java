package me.iamcrk.myfirstapplication;

public class Course {

    private String courseName;
    private int courseHours;
    private double durationMonths;

    public Course(String courseName, int courseHours, double durationMonths) {
        this.courseName = courseName;
        this.courseHours = courseHours;
        this.durationMonths = durationMonths;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public double getDurationMonths() {
        return durationMonths;
    }
}
