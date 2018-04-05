package com.example.ronanlina.attendancechecker;

public class StudentList {

    String StudentId;
    String Name;
    String Section;
    String YearLevel;

    public StudentList(String studentId, String name, String section, String yearLevel) {
        StudentId = studentId;
        Name = name;
        Section = section;
        YearLevel = yearLevel;
    }

    public StudentList() {
    }

    public String getStudentId() {
        return StudentId;
    }

    public String getName() {
        return Name;
    }

    public String getSection() {
        return Section;
    }

    public String getYearLevel() {
        return YearLevel;
    }
}
