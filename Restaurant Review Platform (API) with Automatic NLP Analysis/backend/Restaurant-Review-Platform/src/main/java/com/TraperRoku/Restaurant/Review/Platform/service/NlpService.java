package com.TraperRoku.Restaurant.Review.Platform.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NlpService {
    private final StanfordCoreNLP pipeline;
    public NlpService(StanfordCoreNLP pipeline) {
        this.pipeline = pipeline;
    }
    public NlpService() {
        this(createDefaultPipeline());
    }

    private static StanfordCoreNLP createDefaultPipeline() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        props.setProperty("sentiment.model", "edu/stanford/nlp/models/sentiment/sentiment.ser.gz");
        props.setProperty("sentiment.language", "en");
        return new StanfordCoreNLP(props);
    }

    public int analyzeSentiment(String text){
        if (text == null || text.trim().isEmpty()) {
            return 2;
        }
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        int mainSentiment = 4;
        System.out.println("Pipeline loaded: " + (pipeline != null));



        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            int sentimentScore = switch (sentiment) {
                case "Very Negative" -> 0;
                case "Negative" -> 1;
                case "Neutral" -> 2;
                case "Positive" -> 3;
                case "Very Positive" -> 4;
                default -> 2;
            };


            mainSentiment = Math.min(mainSentiment, sentimentScore);
        }

        return mainSentiment;
    }





}
