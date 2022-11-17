package edu.epitech.dashlord.data.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.data.entities.Role;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.repositories.UserRepository;
import edu.epitech.dashlord.views.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    public class AuthException extends Exception {
    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userRepository.getByUsername(username);
        if (user != null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthRoutes(role).stream()
                .forEach(route -> {
                    RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainView.class);
                });
    }

    public ArrayList<AuthorizedRoute> getAuthRoutes(Role role) {

        ArrayList<AuthorizedRoute> routes = new ArrayList<AuthorizedRoute>();

        if(role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("dashboard", "Dashboard", DashboardView.class));
            routes.add(new AuthorizedRoute("widgetstore", "Widget Store", WidgetStoreView.class));
//            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("dashboard", "Dashboard", DashboardView.class));
            routes.add(new AuthorizedRoute("widgetstore", "Widget Store", WidgetStoreView.class));
//            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
            routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
        }

        return routes;
    }


}
