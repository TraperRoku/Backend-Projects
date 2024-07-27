package com.blogApi.blogApi.controller;


import com.blogApi.blogApi.model.Article;
import com.blogApi.blogApi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public List<Article> getArticles(){
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id){
        Optional<Article> article = articleRepository.findById(id);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article){ // @Valid is used to look for if article has fulfill the requirements in Article
    Article newArticle = articleRepository.save(article);
    return ResponseEntity.ok(newArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable int id){
        Optional<Article> article = articleRepository.findById(id);

        if(article.isPresent()){
            articleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @Valid @RequestBody Article newArticle){
        Optional<Article> article = articleRepository.findById(id).map(articleStream ->{
            articleStream.setAuthor(newArticle.getAuthor());
            articleStream.setUpdatedAt(newArticle.getUpdatedAt());
            articleStream.setContent(newArticle.getContent());
            articleStream.setTitle(newArticle.getTitle());
            return articleRepository.save(articleStream);
        });
        return article.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

}
