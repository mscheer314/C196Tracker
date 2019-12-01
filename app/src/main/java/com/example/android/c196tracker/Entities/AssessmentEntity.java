package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentName;
    private String assessmentType;
    private String assessmentDate;

    public AssessmentEntity(String assessmentName, String assessmentType, String assessmentDate) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentId = " + assessmentId + '\'' +
                ", assessmentName = '" + assessmentName + '\'' +
                ", assessmentType + '" + assessmentType + '\'' +
                ", assessmentDate + '" + assessmentDate +
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
}
