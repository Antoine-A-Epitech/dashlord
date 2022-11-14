package edu.epitech.dashlord.data.repositories;

import edu.epitech.dashlord.data.entities.Widget;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WidgetRepository extends JpaRepository<Widget, Integer> {

}
