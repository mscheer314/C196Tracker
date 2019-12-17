package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "courseId",
        childColumns = "courseId"),
        indices = {@Index("courseId")})

public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentName;
    private String assessmentType;
    private String assessmentDate;

    private int courseId;

    public AssessmentEntity(String assessmentName, String assessmentType, String assessmentDate,
                            int courseId) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentId = " + assessmentId + '\'' +
                ", assessmentName = '" + assessmentName + '\'' +
                ", assessmentType + '" + assessmentType + '\'' +
                ", assessmentDate + '" + assessmentDate +
                ", courseId + '" + courseId +
                '}';
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) { this.assessmentId = assessmentId; }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }
}
