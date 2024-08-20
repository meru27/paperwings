package dev.meru.paperwings.messaging.repository;

import dev.meru.paperwings.messaging.model.Message;
import dev.meru.paperwings.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
