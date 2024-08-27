package com.busanit.spring_study.buva.noticeBoard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setEmail(email);
        siteUser.setUsername(username);

        // 비밀번호 암호화 (DB 에서도 암호화되어 보여짐)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        siteUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(siteUser);
        return siteUser;
    }
}