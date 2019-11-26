package com.example.android.c196tracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class NoteEntity {
    @PrimaryKey
    private int noteId;

    private String noteName;
    private String noteContent;

    public NoteEntity(int noteId, String noteName, String noteContent) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.noteContent = noteContent;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "noteId=" + noteId +
                ", noteName='" + noteName + '\'' +
                ", noteContent'" + noteContent + '\'' +
                '}';
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
