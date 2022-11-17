package edu.epitech.dashlord.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.components.Card;
import edu.epitech.dashlord.data.apiModels.CryptoApi;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.UserWidget;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.services.UserWidgetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@PageTitle("Dashboard")
public class DashboardView extends Div {

    //private FeederThread thread;
    public DashboardView(UserWidgetService userWidgetService) {
        addClassName("container");
//        H1 title = new H1("Dashboard");
//        add(title);

        // Get the current session user
        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        // Get all the user's widget
        List<UserWidget> userWidgets = userWidgetService.getUserWidgets(currentUser);

        userWidgets.forEach(userWidget -> {

            Card card = new Card();
            // Create a new div
//            Div userWidgetDiv = new Div();

            Widget widget = userWidget.getWidget();

            Button removeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL),click -> {
                // Remove the widget from the user widget table
                userWidgetService.removeWidget(userWidget);
            });
            removeButton.addClassName("removeButton");

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setClassName("horizontalLayout");
            horizontalLayout.add(removeButton);

            H1 widgetName = new H1(widget.getName());

            widgetName.addAttachListener(c -> {
                refreshValues(widgetName, widget, c.getUI());
            });

            card.add(horizontalLayout, widgetName );
            add(card);
        });
    }

    private void refreshValues(H1 title, Widget widget, UI ui) {
        RestTemplate restTemplate = new RestTemplate();
        if(widget.getService().getName().equals("Crypto")){
            CryptoApi crypto =  restTemplate.getForObject(widget.getEndpointUrl(), CryptoApi.class);
            ui.access(() -> {
                title.setText(crypto.getData().getAmount());
            });
        }
        // Rerun after a certain amount of time
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                refreshValues(title, widget, ui);
            }
        }, widget.getReloadTime().longValue());
    }
}