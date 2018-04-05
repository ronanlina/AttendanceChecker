package com.example.ronanlina.attendancechecker;

/**
 * Created by Ronan Lina on 21/01/2018.
 */

public class SubjectScheds {
    String subjectCode;
    String subjectName;
    String time;
    String sectionname;
    String teacherid;

    public SubjectScheds(String subjectCode, String subjectName, String time, String section,String teacherid) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.time = time;
        this.sectionname = section;
        this.teacherid = teacherid;
    }

    public SubjectScheds() {
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTime() {
        return time;
    }

    public String getSectionname() {
        return sectionname;
    }

    public String getTeacherid() {
        return teacherid;
    }
}
