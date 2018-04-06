package com.example.ronanlina.attendancechecker;

public class TeacherAccount {

    private String Email;
    private String TeacherId;

    public TeacherAccount(String email, String teacherId) {
        Email = email;
        TeacherId = teacherId;
    }

    public TeacherAccount() {
    }

    public String getEmail() {
        return Email;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }
}
