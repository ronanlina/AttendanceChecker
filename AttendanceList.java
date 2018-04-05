package com.example.ronanlina.attendancechecker;

/**
 * Created by Ronan Lina on 18/03/2018.
 */

public class AttendanceList {
    private String subjId;
    private String studentId;
    private String name;
    private String section;

    public AttendanceList(String subjId, String studentId, String name, String section) {
        this.subjId = subjId;
        this.studentId = studentId;
        this.name = name;
        this.section = section;
    }

    public AttendanceList() {
    }

    public String getSubjId() {
        return subjId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }
}
