package com.example.demo.repository;

import com.example.demo.model.AddNoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class NotesRepository {

    @Autowired
    private Connection conn;

    public void insertNote(AddNoteRequest request) throws SQLException {
        String sql = "insert into notes (note, author, submitted_datetime) values (?, ?, ?)";
        PreparedStatement insertNote = conn.prepareStatement(sql);
        insertNote.setString(1, request.getNote());
        insertNote.setString(2, request.getAuthor());
        insertNote.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

        insertNote.execute();
    }

    public ResultSet getNotes() throws SQLException {
        Statement getNotesStmt = conn.createStatement();
        String sql = "select * from notes";

        return getNotesStmt.executeQuery(sql);
    }

    public ResultSet getNotesByAuthor(String requestAuthor) throws SQLException {
        String sql = "select * from notes where author = ?";
        PreparedStatement getNotesStmt = conn.prepareStatement(sql);
        getNotesStmt.setString(1, requestAuthor);

        return getNotesStmt.executeQuery();
    }
}
