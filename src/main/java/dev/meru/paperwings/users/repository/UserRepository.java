package dev.meru.paperwings.users.repository;

import dev.meru.paperwings.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
