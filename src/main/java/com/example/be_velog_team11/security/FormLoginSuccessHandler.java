package com.example.be_velog_team11.security;

import com.example.be_velog_team11.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    public static final String NICKNAME = "Nickname";

    // ** 5. FormLogin 성공 및 JWT 토큰 생성 **
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        byte[] bytes = userDetails.getUser().getNickname().getBytes(StandardCharsets.UTF_8);
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        String encoded = Base64.getEncoder().encodeToString(bytes);
        response.addHeader(NICKNAME,encoded);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);


    }

}
