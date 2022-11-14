package edu.epitech.dashlord.data.services;

import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.UserWidget;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.repositories.UserWidgetRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWidgetService {
    private final UserWidgetRepository userWidgetRepository;

    public UserWidgetService(UserWidgetRepository userWidgetRepository) {
        this.userWidgetRepository = userWidgetRepository;
    }

    public void addUserWidget(User user, Widget widget) {
        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        userWidgetRepository.save(new UserWidget(currentUser, widget));
    }

    public List<UserWidget> getUserWidgets(User user) {
        List<UserWidget> userWidgets = userWidgetRepository.findUserWidgetsByUser(user);
        return userWidgets;
    }

    public void removeWidget(UserWidget userWidget) {
        userWidgetRepository.deleteById(userWidget.getId());
    }

}
