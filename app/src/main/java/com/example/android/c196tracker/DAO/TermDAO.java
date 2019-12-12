package com.example.android.c196tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Query("SELECT * FROM terms ORDER BY termId ASC")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT * FROM terms WHERE termId = :termId")
    LiveData<List<TermEntity>> getTerm(int termId);

    @Query("SELECT termId FROM terms WHERE termName = :termName")
    LiveData<List<TermEntity>> getTermIdByTermName(String termName);
}
