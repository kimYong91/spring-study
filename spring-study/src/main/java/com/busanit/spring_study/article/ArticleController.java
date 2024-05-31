package com.busanit.spring_study.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    List<ArticleDTO> getAllArticle(){
        return articleService.getAllArticles();
    }

    @GetMapping("{id}")
    ResponseEntity<ArticleDTO> getOne(@RequestBody Long id) {
        ArticleDTO oneArticle = articleService.getArticleById(id);
        if (oneArticle == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(oneArticle);
        }
    }

    @PostMapping
    ArticleDTO createNewArticle(@RequestBody ArticleDTO article) {
        return articleService.createArticle(article);
    }

    @PutMapping("{id}")
    ResponseEntity<ArticleDTO> updateArticle(@RequestBody Long id, @RequestBody ArticleDTO article) {
        ArticleDTO article1 = articleService.updateArticle(id, article);
        if (article1 == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(article1);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<ArticleDTO> deleteArticle(@RequestBody Long id){
        boolean isArticle = articleService.deleteArticle(id);
        if (!isArticle) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
