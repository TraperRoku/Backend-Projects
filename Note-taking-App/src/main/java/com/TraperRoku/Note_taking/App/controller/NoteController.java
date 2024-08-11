package com.TraperRoku.Note_taking.App.controller;


import com.TraperRoku.Note_taking.App.dto.MarkdownDTO;
import com.TraperRoku.Note_taking.App.dto.RuleMatchDTO;
import com.TraperRoku.Note_taking.App.model.Note;
import com.TraperRoku.Note_taking.App.repository.NoteRepository;
import com.TraperRoku.Note_taking.App.service.GrammarCheckService;
import com.TraperRoku.Note_taking.App.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private NoteRepository noteRepository;
    private MarkdownService markdownService;
    private GrammarCheckService grammarCheckService;

    @Autowired
    public NoteController(NoteRepository noteRepository, MarkdownService markdownService, GrammarCheckService grammarCheckService){
        this.grammarCheckService = grammarCheckService;
        this.noteRepository =noteRepository;
        this.markdownService = markdownService;
    }

    @PostMapping("/check-grammer")
    public ResponseEntity<List<RuleMatchDTO>> checkGrammer(@RequestBody String text){
        try {
            List<RuleMatchDTO> matches = grammarCheckService.checkGrammar(text);
            return ResponseEntity.ok(matches);
        } catch (IOException e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/save-markdown")
    public ResponseEntity<Note> saveMarkdown(@RequestBody String markdown){
        Note note = markdownService.saveMarkdown(markdown);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/convert-html")
    public ResponseEntity<String> convertHtml(@RequestBody MarkdownDTO markdownDTO){
        String html = markdownService.convertMarkdownToHtml(markdownDTO.getMarkdown());
        return ResponseEntity.ok(html);
    }
}
