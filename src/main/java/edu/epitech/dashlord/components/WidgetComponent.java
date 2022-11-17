package edu.epitech.dashlord.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.UserWidget;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.services.UserWidgetService;

public class WidgetComponent extends Div {

    public WidgetComponent(Widget widget, UserWidgetService userWidgetService) {

        Card card = new Card();

        Image image = new Image();
        image.setSrc(widget.getService().getLogoUrl());

        H3 header = new H3(widget.getName());

        Div body = new Div();
        body.setText(widget.getDescription());

        Button addWidgetButton = new Button("Add", click -> {

            // Get the user from the current session
            User user = VaadinSession.getCurrent().getAttribute(User.class);

            if(userWidgetService.getUserWidgets(user).stream().map(UserWidget::getWidget).toList().contains(widget)){
                Notification.show("You already have this widget");
            } else {
                userWidgetService.addUserWidget(user, widget);
            };
        });

        card.add(new HorizontalLayout(image,header), body, addWidgetButton);
        add(card);
    }
}