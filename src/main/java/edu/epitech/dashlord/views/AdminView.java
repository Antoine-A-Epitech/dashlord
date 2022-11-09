package edu.epitech.dashlord.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Admin")
public class AdminView extends VerticalLayout {
    public AdminView() {
        H1 title = new H1("Admin");
        add(title);
    }
}
