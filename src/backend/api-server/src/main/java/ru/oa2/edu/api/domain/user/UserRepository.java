package ru.oa2.edu.api.domain.user;

import java.util.List;

public interface UserRepository {

    User findById(long id);
    User findByInternalId(String internalId);
    List<User> findAll();
}
