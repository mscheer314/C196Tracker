package com.example.android.c196tracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.DAO.AssessmentDAO;
import com.example.android.c196tracker.DAO.CourseDAO;
import com.example.android.c196tracker.DAO.CourseMentorDAO;
import com.example.android.c196tracker.DAO.NoteDAO;
import com.example.android.c196tracker.DAO.TermDAO;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.CourseMentorEntity;
import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

public class SchoolTrackerRepository {
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private NoteDAO noteDAO;
    private AssessmentDAO assessmentDAO;
    private CourseMentorDAO courseMentorDAO;

    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<CourseMentorEntity>> mAllCourseMentors;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<AssessmentEntity>> allAssessments;

    private LiveData<List<CourseMentorEntity>> associatedCourseMentor;
    private LiveData<List<NoteEntity>> associatedNotes;
    private LiveData<List<AssessmentEntity>> associatedAssessments;

    private int courseId;
    private int noteId;

    public SchoolTrackerRepository(Application application) {
        SchoolTrackerDatabase db = SchoolTrackerDatabase.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        noteDAO = db.noteDAO();
        assessmentDAO = db.assessmentDAO();
        courseMentorDAO = db.courseMentorDAO();

        allTerms = termDAO.getAllTerms();
        allCourses = courseDAO.getAllCourses();
        mAllCourseMentors = courseMentorDAO.getAllCourseMentors();
        allNotes = noteDAO.getAllNotes();
        allAssessments = assessmentDAO.getAllAssessments();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public LiveData<List<CourseEntity>> getAllCourses() { return allCourses; }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<CourseEntity>> getAssociatedCourses(int termId) {
        return courseDAO.getAllAssociatedCourses(termId);
    }

    public LiveData<List<NoteEntity>> getAssociatedNotes(int courseId) {
        return associatedNotes;
    }

    public void insert(TermEntity termEntity) {
        new insertAsyncTask1(termDAO).execute(termEntity);
    }

    public void insert(CourseEntity courseEntity) {
        new insertAsyncTask2(courseDAO).execute(courseEntity);
    }

    public void insert(NoteEntity noteEntity) {
        new insertAsyncTask3(noteDAO).execute(noteEntity);
    }

    public void insert(AssessmentEntity assessmentEntity) {
        new insertAsyncTask4(assessmentDAO).execute(assessmentEntity);
    }

    public void insert(CourseMentorEntity courseMentorEntity) {
        new insertAsyncTask5(courseMentorDAO).execute(courseMentorEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<TermEntity, Void, Void> {
        private TermDAO asyncTaskDAO;

        insertAsyncTask1(TermDAO dao) {
            asyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            asyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDAO asyncCourseDAO;

        insertAsyncTask2(CourseDAO dao) {
            asyncCourseDAO = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            asyncCourseDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask3 extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO asyncNoteDAO;

        insertAsyncTask3(NoteDAO dao) {
            asyncNoteDAO = dao;
        }

        @Override
        protected Void doInBackground(final NoteEntity... params) {
            asyncNoteDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask4 extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDAO asyncAssessmentDAO;

        insertAsyncTask4(AssessmentDAO dao) {
            asyncAssessmentDAO = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            asyncAssessmentDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask5 extends AsyncTask<CourseMentorEntity, Void, Void> {
        private CourseMentorDAO asyncCourseMentorDAO;

        insertAsyncTask5(CourseMentorDAO dao) { asyncCourseMentorDAO = dao; }

        @Override
        protected Void doInBackground(final CourseMentorEntity... params) {
            asyncCourseMentorDAO.insert(params[0]);
            return null;
        }
    }
}
