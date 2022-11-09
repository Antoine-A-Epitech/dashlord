package edu.epitech.dashlord.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
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

    private TextField usernameField;

    private EmailField emailField;

    private PasswordField passwordField;

    private Button signupButton;

    UserRepository userRepository;

    public SignupView(UserRepository userRepository) {
        this.userRepository = userRepository;
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Signup");

        usernameField = new TextField();
        usernameField.setLabel("Username");

        emailField = new EmailField();
        emailField.setLabel("Email");
        emailField.getElement().setAttribute("name", "email");
        emailField.setPlaceholder("john.doe@email.com");
        emailField.setErrorMessage("Enter a valid email : example@example.com");
        emailField.setClearButtonVisible(true);

        passwordField = new PasswordField();
        passwordField.setLabel("Password");

        signupButton = new Button("Login");
        signupButton.addClickListener(event -> {
            try {
                registerUser(event);
                UI.getCurrent().navigate("login");
            } catch (Error e) {
                Notification.show("Username already exists.");
            }
        });

        Button signinButton = new Button("Sign In");
        signinButton.addClickListener(click -> UI.getCurrent().navigate("login"));

        add(title , usernameField, emailField, passwordField, signupButton, signinButton);
    }

    private void registerUser(ClickEvent event) {
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
