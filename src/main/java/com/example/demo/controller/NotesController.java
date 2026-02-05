package com.example.demo.controller;

import com.example.demo.model.AddNoteRequest;
import com.example.demo.model.ResponseNote;
import com.example.demo.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/add-note")
    public void insertNote(@RequestBody AddNoteRequest request) {
        notesService.insertNote(request);
    }

    @GetMapping("/get-notes")
    public List<ResponseNote> getNotes() {
        return notesService.getNotes();
    }

    @GetMapping("/get-notes-by-author")
    public List<ResponseNote> getNotes(@RequestParam String requestAuthor) {
        return notesService.getNotesByAuthor(requestAuthor);
    }
}
