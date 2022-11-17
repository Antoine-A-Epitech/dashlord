package edu.epitech.dashlord.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import edu.epitech.dashlord.components.WidgetComponent;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.repositories.WidgetRepository;
import edu.epitech.dashlord.data.services.UserWidgetService;

import java.util.List;

@PageTitle("Widget Store")
public class WidgetStoreView extends Div {

    public WidgetStoreView(WidgetRepository widgetRepository, UserWidgetService userWidgetService){
        addClassName("container");

        // Add a widget component for each widget in the db
        List<Widget> widgets = widgetRepository.findAll();

        widgets.forEach(widget -> {
            add(new WidgetComponent(widget, userWidgetService));
        });

        // To remove
        // ----------------------------------------------------------------
        widgets.forEach(widget -> {
            add(new WidgetComponent(widget, userWidgetService));
        });widgets.forEach(widget -> {
            add(new WidgetComponent(widget, userWidgetService));
        });widgets.forEach(widget -> {
            add(new WidgetComponent(widget, userWidgetService));
        });widgets.forEach(widget -> {
            add(new WidgetComponent(widget, userWidgetService));
        });
        //----------------------------------------------------------------
    }
}
