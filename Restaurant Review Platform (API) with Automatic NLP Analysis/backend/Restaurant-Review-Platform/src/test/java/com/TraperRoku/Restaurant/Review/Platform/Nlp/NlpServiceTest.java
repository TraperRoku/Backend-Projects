package com.TraperRoku.Restaurant.Review.Platform.Nlp;

import com.TraperRoku.Restaurant.Review.Platform.service.NlpService;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NlpServiceTest {

    private static NlpService nlpService;

    @Before
    public void init() {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");


        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        nlpService = new NlpService(pipeline);
    }

    @Test
    public void shouldDetectVeryPositiveSentiment() {
        assertEquals(5, nlpService.analyzeSentiment("This restaurant is amazing!"));
    }

    @Test
    public void shouldDetectPositiveSentiment() {
        assertEquals(4, nlpService.analyzeSentiment("Good food"));
    }

    @Test
    public void shouldHandleEmptyText() {
        assertEquals(3, nlpService.analyzeSentiment(""));
    }
    @Test
    public void shouldDetectNegativeSentiment() {
        assertEquals(2, nlpService.analyzeSentiment("Food was bad but it was cheap"));
    }

    @Test
    public void shouldDetectVeryNegativeSentiment() {
        String extremeText1 = "This is the WORST restaurant ever! Absolutely disgusting, revolting, and horrifying. "
                + "Every bite made me want to vomit. It's a disgrace to food. "
                + "Whoever made this should NEVER be allowed to cook again. "
                + "I felt sick for days. Just shut this place down!";
        assertEquals(1, nlpService.analyzeSentiment(extremeText1));
        //NIE MA 1
    }

}
