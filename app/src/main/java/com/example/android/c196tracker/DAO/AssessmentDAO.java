package com.example.android.c196tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.c196tracker.Entities.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();
}

