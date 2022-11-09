package edu.epitech.dashlord.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Logout")
public class LogoutView extends VerticalLayout {
    public LogoutView() {
        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("login");
            VaadinSession.getCurrent().getSession().invalidate();
            VaadinSession.getCurrent().close();
        });

        add(logoutButton);
    }
}
