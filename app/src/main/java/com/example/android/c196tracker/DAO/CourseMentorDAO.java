package com.example.android.c196tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.c196tracker.Entities.CourseMentorEntity;

import java.util.List;

@Dao
public interface CourseMentorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseMentorEntity courseMentor);

    @Query("DELETE FROM course_mentors")
    void deleteAllCourseMentors();

    @Query("SELECT * FROM course_mentors ORDER BY courseMentorId ASC")
    LiveData<List<CourseMentorEntity>> getAllCourseMentors();

    @Query("SELECT * FROM course_mentors WHERE courseId= :courseId ORDER BY courseId ASC")
    LiveData<List<CourseMentorEntity>> getCourseMentor(int courseId);
}
