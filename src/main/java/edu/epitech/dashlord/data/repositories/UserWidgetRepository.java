package edu.epitech.dashlord.data.repositories;

import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.UserWidget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWidgetRepository extends JpaRepository<UserWidget, Integer> {
    List<UserWidget> findUserWidgetsByUser(User user);
}
