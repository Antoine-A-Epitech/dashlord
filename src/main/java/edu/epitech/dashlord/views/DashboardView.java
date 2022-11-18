package edu.epitech.dashlord.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import edu.epitech.dashlord.components.Card;
import edu.epitech.dashlord.data.apiModels.CryptoApi;
import edu.epitech.dashlord.data.apiModels.JokesApi;
import edu.epitech.dashlord.data.apiModels.WeatherApi;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.entities.UserWidget;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.services.UserWidgetService;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@PageTitle("Dashboard")
public class DashboardView extends Div {

    private String weatherEndpointUrl;

    public DashboardView(UserWidgetService userWidgetService) {
        addClassName("container");

        // Get the current session user
        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        // Get all the user's widget
        List<UserWidget> userWidgets = userWidgetService.getUserWidgets(currentUser);

        userWidgets.forEach(userWidget -> {

            Card card = new Card();

            Widget widget = userWidget.getWidget();

            // Remove the button from the user's dashboard
            Button removeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL),click -> {
                // Remove the widget from the user widget table
                userWidgetService.removeWidget(userWidget);

                userWidget.getTimer().cancel();

                card.setVisible(false);
            });
            removeButton.addClassName("removeButton");

            H2 widgetName = new H2(widget.getName());


            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setClassName("horizontalLayout");
            horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            horizontalLayout.add(widgetName, removeButton);


            VerticalLayout widgetBody = new VerticalLayout();
            widgetBody.addClassName("widgetBody");
            widgetBody.setAlignItems(FlexComponent.Alignment.CENTER);


            if (widget.getService().getName().equalsIgnoreCase("Weather")) {
                weatherEndpointUrl = widget.getEndpointUrl() + "&q=montpellier" + "&aqi=no";

                VerticalLayout searchBar = new VerticalLayout();

                searchBar.setAlignItems(FlexComponent.Alignment.CENTER);
                searchBar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
                TextField cityNameInput = new TextField("City");
                Button searchButton = new Button("Search");


                searchButton.addClickListener(click -> {
                    weatherEndpointUrl = widget.getEndpointUrl() + "&q=" + cityNameInput.getValue().toLowerCase() + "&aqi=no";
                });
                searchBar.add(cityNameInput, searchButton);
                widgetBody.add(searchBar);
            }

            widgetName.addAttachListener(c -> {
                refreshValues(widgetBody, userWidget, c.getUI());
            });

            card.add(horizontalLayout, widgetBody);

            add(card);
        });
    }

    private void refreshValues(VerticalLayout widgetBody, UserWidget userWidget, UI ui) {
        RestTemplate restTemplate = new RestTemplate();
        if(userWidget.getWidget().getService().getName().equalsIgnoreCase("Crypto")){
            CryptoApi crypto =  restTemplate.getForObject(userWidget.getWidget().getEndpointUrl(), CryptoApi.class);
            ui.access(() -> {
                widgetBody.removeAll();
                H4 priceHeader = new H4(String.format("%s USD", crypto.getData().getAmount()));
                widgetBody.add(priceHeader);
            });
        } else if (userWidget.getWidget().getService().getName().equalsIgnoreCase("Jokes")) {
            JokesApi joke = restTemplate.getForObject(userWidget.getWidget().getEndpointUrl(), JokesApi.class);
            ui.access(() -> {
                widgetBody.removeAll();
                widgetBody.add(new Paragraph(joke.getJoke()));
            });
        } else if (userWidget.getWidget().getService().getName().equalsIgnoreCase("Weather")) {
            WeatherApi weather = restTemplate.getForObject(weatherEndpointUrl, WeatherApi.class);
            ui.access(() -> {
                widgetBody.removeAll();

                // For the icon
                Image icon = new Image();
                icon.setSrc("http:" + weather.getCurrent().getCondition().getIcon());

                // For the temperature
                H3 temperature = new H3(weather.getCurrent().getTemp_c() + "° C");

                // For the location
                Paragraph location = new Paragraph(weather.getLocation().getName() + ", " + weather.getLocation().getRegion());

                VerticalLayout searchBar = new VerticalLayout();

                searchBar.setAlignItems(FlexComponent.Alignment.CENTER);
                searchBar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
                TextField cityNameInput = new TextField("City");
                Button searchButton = new Button("Search");


                searchButton.addClickListener(click -> {
                    userWidget.getTimer().cancel();

                    weatherEndpointUrl = userWidget.getWidget().getEndpointUrl() + "&q=" + cityNameInput.getValue().toLowerCase() + "&aqi=no";

                    refreshValues(widgetBody, userWidget, ui);
                });
                searchBar.add(cityNameInput, searchButton);


                widgetBody.add(icon, temperature, location, searchBar);
            });
        }

        // Rerun after a certain amount of time
        Timer timer = new Timer();

        userWidget.setTimer(timer);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshValues(widgetBody, userWidget, ui);
            }
        }, userWidget.getWidget().getReloadTime().longValue());

    }
}