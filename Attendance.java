package com.example.ronanlina.attendancechecker;

/**
 * Created by Ronan Lina on 13/03/2018.
 */

public class Attendance {

    private String subjectId;
    private String studentId;
    private String name;
    private String section;
    private String dateAndTime;
    private String attendanceType;

    public Attendance(String subjectId, String studentId, String name, String section, String dateAndTime, String attendanceType) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.name = name;
        this.section = section;
        this.dateAndTime = dateAndTime;
        this.attendanceType = attendanceType;
    }

    public Attendance() {
    }

    public String getSubjectId() {
        return subjectId;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getAttendanceType() {
        return attendanceType;
    }
}
