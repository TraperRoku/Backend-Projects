package com.TraperRoku.Note_taking.App.service;

import com.TraperRoku.Note_taking.App.dto.RuleMatchDTO;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Polish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrammarCheckService {

    private JLanguageTool languageTool;

    public GrammarCheckService() {
        languageTool = new JLanguageTool(new Polish());
    }

    public List<RuleMatchDTO> checkGrammar(String text) throws IOException {
        List<RuleMatch> matches = languageTool.check(text);

        return matches.stream()
                .map(match -> new RuleMatchDTO(match.getMessage(), match.getFromPos(), match.getToPos() - match.getFromPos()))
                .collect(Collectors.toList());
    }
}

