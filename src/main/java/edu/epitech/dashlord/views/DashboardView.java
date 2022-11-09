package edu.epitech.dashlord.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Home")
public class DashboardView extends VerticalLayout {
    public DashboardView() {
        H1 title = new H1("Dashboard");

        add(title);
    }
}
