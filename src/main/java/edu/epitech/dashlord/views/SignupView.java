package edu.epitech.dashlord.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.epitech.dashlord.data.entities.Role;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.repositories.UserRepository;

@Route("signup")
@PageTitle("Signup")
@AnonymousAllowed
public class SignupView extends VerticalLayout {

    private final TextField usernameField;

    private final EmailField emailField;

    private final PasswordField passwordField;

    UserRepository userRepository;

    public SignupView(UserRepository userRepository) {
        this.userRepository = userRepository;
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Signup");

        usernameField = new TextField("Username");
        usernameField.setRequired(true);

        emailField = new EmailField("Email","john.doe@email.com");
        emailField.getElement().setAttribute("name", "email");
        emailField.setErrorMessage("Enter a valid email : example@example.com");
        emailField.setClearButtonVisible(true);

        passwordField = new PasswordField("Password");
        passwordField.setRequired(true);

        Button signupButton = new Button("Signup", event -> {
            try {
                registerUser(event);
                UI.getCurrent().navigate("login");
            } catch (Error e) {
                Notification.show(e.getMessage());
            }
        });

        signupButton.addClickShortcut(Key.ENTER);

        Button signinButton = new Button("Login",click -> UI.getCurrent().navigate("login"));

        Div div = new Div(title , usernameField, emailField, passwordField, signupButton, signinButton);
        div.setClassName("auth_card");
        add(div);
    }

    private void registerUser(ClickEvent event) throws Error {
        if (usernameField.getValue().equals("")) throw new Error ("A username is required.");
        if (emailField.getValue().equals("")) throw new Error ("An email is required.");
        if (passwordField.getValue().equals("")) throw new Error ("A password is required.");

        // In here we will do the checking for the registration
        User user = userRepository.getByUsername(usernameField.getValue());
        // If no user was found in the db
        if (user == null) {
            // Creating an admin account for the first time
            if (usernameField.getValue().equals("admin")) {
                userRepository.save(new User(usernameField.getValue(), emailField.getValue(), passwordField.getValue(), Role.ADMIN));
            } else {
                // Create a user account
                userRepository.save(new User(usernameField.getValue(), emailField.getValue(), passwordField.getValue(), Role.USER));
            }
        } else {
            throw new Error("Username already exists.");
        }

    }

}
