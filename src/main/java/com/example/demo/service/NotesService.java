package com.example.demo.service;

import com.example.demo.model.AddNoteRequest;
import com.example.demo.model.ResponseNote;
import com.example.demo.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public void insertNote(AddNoteRequest request) {
        try {
            notesRepository.insertNote(request);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<ResponseNote> getNotes() {
        try {
            ResultSet resultSet = notesRepository.getNotes();
            return buildNotesResponse(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<ResponseNote> getNotesByAuthor(String requestAuthor) {
        try {
            ResultSet resultSet = notesRepository.getNotesByAuthor(requestAuthor);
            return buildNotesResponse(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private List<ResponseNote> buildNotesResponse(ResultSet resultSet) throws SQLException {
        List<ResponseNote> responseNotesList = new ArrayList<>();
        while (resultSet.next()) {
            String note = resultSet.getString("note");
            String author = resultSet.getString("author");
            LocalDateTime localDateTime = resultSet.getTimestamp("submitted_datetime").toLocalDateTime();
            System.out.println("Note: " + note + ", Author: " + author + ", Submitted: " + localDateTime);
            ResponseNote responseNote = new ResponseNote();
            responseNote.setNote(note);
            responseNote.setAuthor(author);
            responseNote.setSubmittedDateTime(localDateTime);
            responseNotesList.add(responseNote);
        }
        return responseNotesList;
    }
}
