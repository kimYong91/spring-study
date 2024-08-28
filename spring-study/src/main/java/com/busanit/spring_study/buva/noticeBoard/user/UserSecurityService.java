package com.busanit.spring_study.buva.noticeBoard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 만드는 순서 19
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    // @Autowired 이게 없어도 되는 이유 > @RequiredArgsConstructor 이게 있어서
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 로그인 처리 로직 구현
        // 디비 findByUsername() 호출 후
        Optional<SiteUser> _siteUser = userRepository.findByUsername(username);  // 임시적으로 이 코드내에서만 비교할 때 사용할 키워드 이름에 '_이름' 으로 준다.
        // 데이터 있는지 확인
        if (_siteUser.isEmpty()) {
            throw  new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        // 권한설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        // 패스워드 맞는지 확인
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
