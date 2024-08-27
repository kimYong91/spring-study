package com.busanit.spring_study.buva.noticeBoard.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(length = 18)
    private String password;

    @Column(unique = true)
    private String email;
}
