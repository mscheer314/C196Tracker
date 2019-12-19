package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.CourseMentorEntity;

import java.util.List;

public class CourseMentorViewModel extends AndroidViewModel {
    int courseId;
    private SchoolTrackerRepository repository;
    private LiveData<List<CourseMentorEntity>> allCourseMentors;
    private LiveData<List<CourseMentorEntity>> associatedCourseMentor;

    public CourseMentorViewModel(Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);

        allCourseMentors = repository.getAllCourseMentors();
        associatedCourseMentor = repository.getAssociatedCourseMentor(courseId);
    }

    public LiveData<List<CourseMentorEntity>> getAllCourseMentors() { return allCourseMentors; }

    public LiveData<List<CourseMentorEntity>> getAssociatedCourseMentor(int courseId) {
        return associatedCourseMentor;
    }

    public void insert(CourseMentorEntity courseMentorEntity) {
        repository.insert(courseMentorEntity);
    }
}
