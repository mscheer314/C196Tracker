package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termId;
    private SchoolTrackerRepository repository;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<CourseEntity>> associatedCourses;

    public CourseViewModel(Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);

        allCourses = repository.getAllCourses();
        associatedCourses = repository.getAssociatedCourses(termId);
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<CourseEntity>> getAssociatedCourses(int termId) {
        return repository.getAssociatedCourses(termId);
    }

    public void insert(CourseEntity courseEntity) {
        repository.insert(courseEntity);
    }
}
