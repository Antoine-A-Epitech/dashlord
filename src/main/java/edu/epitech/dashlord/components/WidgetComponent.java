package edu.epitech.dashlord.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.services.UserWidgetService;


public class WidgetComponent extends Div {

    public WidgetComponent(Widget widget, UserWidgetService userWidgetService) {
        //addClassName('widget');
        H1 header = new H1(widget.getName());

        Div body = new Div();
        body.setText(widget.getDescription());

        Button addWidgetButton = new Button("Add");

        addWidgetButton.addClickListener(click -> {
            // Get the user from the current session
            User user = VaadinSession.getCurrent().getAttribute(User.class);

            if(userWidgetService.getUserWidgets(user).stream().map(userWidget -> userWidget.getWidget()).toList().contains(widget)){
                Notification.show("You already have this widget");
            } else {
                userWidgetService.addUserWidget(user, widget);
            };
        });

        add(header, body, addWidgetButton);
    }
}
