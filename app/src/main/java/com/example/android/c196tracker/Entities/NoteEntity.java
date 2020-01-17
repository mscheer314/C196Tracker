package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String noteContent;
    private int courseId;

    public NoteEntity(String noteContent, int courseId) {
        this.noteContent = noteContent;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "noteId=" + noteId +
                ", noteContent'" + noteContent + '\'' +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
