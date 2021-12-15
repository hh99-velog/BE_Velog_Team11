package com.example.be_velog_team11.security.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
FormLoginFilter: 회원 폼 로그인 요청 시 username/password 인증
1.POST "/user/login" API에 대해서만 동작 필요
 1) GET '/user/login" 처리되지 않게 하기 위해 API 주소 변경 (회원 로그인 페이지 변경, GET "/user/login" -> GET "/user/loginView")
2. Client 로부터 username, password 전달받아 인증 수행
3. 인증 성공 시
 1) FormLoginSuccessHandler 통해 JWT Token 생성
 2) 이후 Client 에서는 모든 API 응답 Header에 JWT Token 포함하여 인증
 */
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
    final private ObjectMapper objectMapper;

    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            // ** 1. 클라이언트 JSON -> 자바 객체 형태로 변경 **
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();

            // ** 2. username, password 요청 변수 생성 **
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            throw new RuntimeException("username, password 입력이 필요합니다. (JSON)");
        }

        // ** 3. 인증 요청 **
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
