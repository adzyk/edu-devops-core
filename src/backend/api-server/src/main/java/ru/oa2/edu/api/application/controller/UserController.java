package ru.oa2.edu.api.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.oa2.edu.api.application.services.RepositoryService;
import ru.oa2.edu.api.domain.user.UserInfo;
import ru.oa2.edu.api.domain.user.UserRepository;
import ru.oa2.edu.api.domain.user.UserStatistic;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/current")
    public UserInfo getCurrentUser(Authentication authentication) {
        var jwtAuth = ((JwtAuthenticationToken) authentication).getTokenAttributes();

        var user = userRepository.findByInternalId(authentication.getName());
        return new UserInfo(
                authentication.getName(),
                jwtAuth.get("family_name").toString(),
                jwtAuth.get("given_name").toString(),
                jwtAuth.get("email").toString(),
                repositoryService.getUserStatistic(user.getId())
                );
    }
}
