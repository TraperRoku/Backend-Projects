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
            return 3;
        }
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        int mainSentiment = 5;
        int longest = 0;

        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            int length = sentence.toString().length();

            if(length > longest){
                longest = length;
                int sentimentScore  = switch (sentiment){
                    case "Very negative" -> 1;
                    case "Negative" -> 2;
                    case "Neutral" -> 3;
                    case "Positive" -> 4;
                    case "Very positive" -> 5;
                    default -> 3;
                };
                mainSentiment = Math.min(mainSentiment, sentimentScore);
            }
        }
        return mainSentiment;
    }





}
