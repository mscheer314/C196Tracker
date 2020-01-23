package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "courses", foreignKeys = @ForeignKey(entity = TermEntity.class,
        parentColumns = "termId",
        childColumns = "termId",
        onDelete = RESTRICT),
        indices = {@Index("termId")})

public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseName;
    private Date courseStart;
    private Date courseEnd;
    private String courseStatus;
    private String courseMentorName;
    private String courseMentorEmail;
    private String courseMentorPhone;

    private int termId;

    public CourseEntity(String courseName, Date courseStart, Date courseEnd,
                        String courseStatus, String courseMentorName, String courseMentorEmail,
                        String courseMentorPhone, int termId) {
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.termId = termId;
        this.courseMentorName = courseMentorName;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhone = courseMentorPhone;
    }

    @Ignore
    public CourseEntity(int courseId, String courseName, Date courseStart, Date courseEnd,
                        String courseStatus, String courseMentorName, String courseMentorEmail,
                        String courseMentorPhone, int termId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.termId = termId;
        this.courseMentorName = courseMentorName;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhone = courseMentorPhone;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseMentorName='" + courseMentorName + '\'' +
                ", courseMentorEmail='" + courseMentorEmail + '\'' +
                ", courseMentorPhone='" + courseMentorPhone + '\'' +
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

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }
}
