package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses", foreignKeys = @ForeignKey(entity = TermEntity.class,
        parentColumns = "termId",
        childColumns = "termId"),
        indices = {@Index("termId")})

public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;

    private int termId;

    public CourseEntity(String courseName, String courseStart, String courseEnd,
                        String courseStatus, int termId) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.termId = termId;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", termId='" + termId + '\'' +
                '}';

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getTermId() { return termId; }

    public void setTermId(int termId) { this.termId = termId; }

    public String getCourseStatus() { return courseStatus; }

    public void setCourseStatus(String courseStatus) { this.courseStatus = courseStatus; }
}
