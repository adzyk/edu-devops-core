package ru.oa2.edu.api.application.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.oa2.edu.api.application.database.UserJpaRepository;
import ru.oa2.edu.api.domain.user.User;

import java.io.IOException;
import java.util.function.Supplier;

@Slf4j
@Component
public class SaveUser implements AuthorizationEventPublisher {

    @Autowired
    UserJpaRepository userRepository;

    @Override
    public <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
        if (userRepository.findByInternalId(authentication.get().getName()) == null) {
            userRepository.save(new User(authentication.get().getName()));
            log.info("User with name {} was added", authentication.get().getName());
        }
    }
}