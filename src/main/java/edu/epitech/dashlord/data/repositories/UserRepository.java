package edu.epitech.dashlord.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.epitech.dashlord.data.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsername(String username);

}
