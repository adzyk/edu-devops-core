package ru.oa2.edu.api.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oa2.edu.api.domain.user.User;
import ru.oa2.edu.api.domain.user.UserRepository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
}
