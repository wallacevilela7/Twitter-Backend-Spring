package wvsdev.twitterapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wvsdev.twitterapp.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
