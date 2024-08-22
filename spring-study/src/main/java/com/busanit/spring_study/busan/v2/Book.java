package com.busanit.spring_study.busan.v2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity  // JPA 엔티티임을 선언 (엔티티는 매핑을 뜻하고, 번역과 같은 말로 봐도 될 듯
//                              번역이란 자바의 언어로 작성하였으나 다른 데이터베이스와
//                              연결되어도 추가 작업없이 바로 기능하고 만들어진다는것을 번역으로 예를 듬)
public class Book {

    @Id     // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    private Long id;

    private String title;
    private String author;

    // 생성자 : 전체 생성자, 기본 생성자
    public Book() {}
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // 게터, 세터
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}