package wvsdev.twitterapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wvsdev.twitterapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
