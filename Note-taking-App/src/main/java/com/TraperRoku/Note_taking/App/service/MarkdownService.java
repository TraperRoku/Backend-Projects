package com.TraperRoku.Note_taking.App.service;

import com.TraperRoku.Note_taking.App.model.Note;
import com.TraperRoku.Note_taking.App.repository.NoteRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MarkdownService {

    private NoteRepository noteRepository;

    @Autowired
    public MarkdownService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public String convertMarkdownToHtml(String markdown){
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public Note saveMarkdown(String markdown){
        Note note = new Note();
        note.setContent(markdown);
        return noteRepository.save(note);
    }

}
