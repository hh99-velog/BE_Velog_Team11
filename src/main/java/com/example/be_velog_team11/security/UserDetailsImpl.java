package com.example.be_velog_team11.security;

import com.example.be_velog_team11.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
TODO: API 사용시마다 매번 User DB 조회 필요
원인: UserDetailsImp 에서 User Entity 객체 가지고 있기 떄문
해결
 1. 해결을 위해서는 UserDetailsImpl 에 User 객체를 저장하지 않도록 수정
     - 인증 사용자 정보는 스프링 시큐리티가 제공하는 UserDetails 인터페이스 형태만 맞춰서 구현하면됨
     - 멤버 변수는 마음대로 수정 가능
     ex) UserDetailsImpl 에 userId, username, role 만 저장
     public class UserDetailsImpl implements UserDetails {
            // 삭제
            private User user;

            // 추가
            private Long userId;
            private String username;
            private UserRoleEnum role;
     }
  2. JWT 에 userId, username, role 정보를 암호화/복호화하여 사용
*/

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}