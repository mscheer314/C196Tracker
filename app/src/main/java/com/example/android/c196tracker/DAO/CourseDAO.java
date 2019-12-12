package com.example.android.c196tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.c196tracker.Entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Query("DELETE FROM courses")
    void deleteAllCourses();

    @Query("SELECT * FROM courses ORDER BY courseId ASC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT * FROM courses WHERE termId= :termId ORDER BY courseId ASC")
    LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId);
}
