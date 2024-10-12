package ru.oa2.edu.api.application.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


import java.io.IOException;

@Slf4j
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);

        HttpSession session = request.getSession(true);

        try {
            var jwtAuth = ((JwtAuthenticationToken) authentication).getTokenAttributes();
            var sub = (String) jwtAuth.get("sub");
            log.info(String.format("SUB: %s", sub));
        } catch (Exception e) {
            log.error("Error in getting User info", e);
        }
    }

}