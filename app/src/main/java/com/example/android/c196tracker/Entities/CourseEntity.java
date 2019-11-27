package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class CourseEntity {
    @PrimaryKey (autoGenerate = true)
    private int courseId;

    private String courseName;
    private String courseStart;
    private String courseEnd;

    public CourseEntity(String courseName, String courseStart, String courseEnd) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
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
}
