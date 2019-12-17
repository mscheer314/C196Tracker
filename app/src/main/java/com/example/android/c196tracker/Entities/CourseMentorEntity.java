package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_mentors", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "courseId",
        childColumns = "courseId"),
        indices = {@Index("courseId")})

public class CourseMentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseMentorId;

    private String courseMentorName;
    private String courseMentorPhoneNumber;
    private String courseMentorEmail;

    private int courseId;

    public CourseMentorEntity(String courseMentorName, String courseMentorPhoneNumber,
                              String courseMentorEmail, int courseId) {
        this.courseMentorName = courseMentorName;
        this.courseMentorPhoneNumber = courseMentorPhoneNumber;
        this.courseMentorEmail = courseMentorEmail;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CourseMentorEntity{" +
                "courseMentorId=" + courseMentorId +
                ", courseMentorName='" + courseMentorName + '\'' +
                ", courseMentorPhoneNumber='" + courseMentorPhoneNumber + '\'' +
                ", courseMentorEmail='" + courseMentorEmail + '\'' +
                ", courseId=" + courseId +
                '}';
    }

    public int getCourseMentorId() {
        return courseMentorId;
    }

    public void setCourseMentorId(int courseMentorId) {
        this.courseMentorId = courseMentorId;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public String getCourseMentorPhoneNumber() {
        return courseMentorPhoneNumber;
    }

    public void setCourseMentorPhoneNumber(String courseMentorPhoneNumber) {
        this.courseMentorPhoneNumber = courseMentorPhoneNumber;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
