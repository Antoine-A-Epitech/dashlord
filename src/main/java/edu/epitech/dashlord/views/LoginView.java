package edu.epitech.dashlord.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.epitech.dashlord.data.services.AuthService;

@Route(value = "login")
@RouteAlias("")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(AuthService authService) {

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        var usernameField = new TextField("Username");
        usernameField.setRequired(true);
        var passwordField = new PasswordField("Password");
        passwordField.setRequired(true);
        var loginButton = new Button("Login",event -> {
            try{
                authService.authenticate(usernameField.getValue(), passwordField.getValue());
                UI.getCurrent().navigate("dashboard");
            } catch (AuthService.AuthException e) {
                Notification.show("Wrong Credentials");
            }
        });

        loginButton.addClickShortcut(Key.ENTER);
        Button signupButton = new Button("Signup",click -> UI.getCurrent().navigate("signup"));

        Div div = new Div();
        div.setClassName("auth_card");
        div.add(new H1("Welcome to Dashlord"), usernameField, passwordField, loginButton, signupButton);
        add(div);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}