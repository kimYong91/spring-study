package com.busanit.spring_study.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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


    // 커스텀 쿼리 요청
    @GetMapping("/title/{title}")
    public List<ArticleDTO> getArticleByTitleContaining(@PathVariable String title) {
        return articleService.getArticleByTitleContaining(title);
    }

    @GetMapping("/author/{author}")
    public List<ArticleDTO> getArticleByAuthor(@PathVariable String author) {
        return articleService.getArticleByAuthor(author);
    }




    // 게시글 페이징, 정렬된 상태로 조회하기
    @GetMapping("/page")
    public Page<ArticleDTO> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<ArticleDTO> articles = articleService.getArticles(page, size, sortBy);
        return articles;
    }
    // 특정 저자의 게시글 페이징, 정렬된 상태로 조회
    @GetMapping("/author")
    public Page<ArticleDTO> getArticlesByAuthor(
            @RequestParam String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<ArticleDTO> articles = articleService.getArticlesByAuthor(author, page, size, sortBy);
        return articles;
    }
}
