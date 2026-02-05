package com.example.demo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@CrossOrigin(origins = "*")
@RestController
public class NotesController {

    @Value("${db_url}")
    private String dbUrl;

    @Value("${db_user}")
    private String dbUser;

    @Value("${db_pass}")
    private String dbPass;

    @PostMapping("/add-note")
    public void insertNote(@RequestBody AddNoteRequest request) throws SQLException {
        Connection conn = getConnection();

        String sql = "insert into notes (note, author, submitted_datetime) values (?, ?, ?)";
        PreparedStatement insertNote = conn.prepareStatement(sql);
        insertNote.setString(1, request.getNote());
        insertNote.setString(2, request.getAuthor());
        insertNote.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

        insertNote.execute();
    }

    @GetMapping("/get-notes")
    public List<ResponseNote> getNotes() throws SQLException {
        Connection conn = getConnection();

        String sql = "select * from notes";
        Statement getNotes = conn.createStatement();

        ResultSet resultSet = getNotes.executeQuery(sql);
        return buildNotesResponse(resultSet);
    }

    @GetMapping("/get-notes-by-author")
    public List<ResponseNote> getNotes(@RequestParam String requestAuthor) throws SQLException {
        Connection conn = getConnection();

        String sql = "select * from notes where author = ?";
        PreparedStatement getNotes = conn.prepareStatement(sql);
        getNotes.setString(1, requestAuthor);

        ResultSet resultSet = getNotes.executeQuery();
        return buildNotesResponse(resultSet);
    }

    private static List<ResponseNote> buildNotesResponse(ResultSet resultSet) throws SQLException {
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

    public Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(dbUrl, connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}
