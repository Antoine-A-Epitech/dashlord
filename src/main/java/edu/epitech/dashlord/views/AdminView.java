package edu.epitech.dashlord.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import edu.epitech.dashlord.data.entities.Widget;
import edu.epitech.dashlord.data.entities.WidgetService;
import edu.epitech.dashlord.data.repositories.WidgetRepository;
import edu.epitech.dashlord.data.services.WidgetServiceService;

import java.util.List;

@PageTitle("Admin")
public class AdminView extends VerticalLayout {


    public AdminView(WidgetServiceService widgetServiceService, WidgetRepository widgetRepository) {

        VerticalLayout serviceForm = new VerticalLayout();

        H1 title = new H1("Add a service");
        TextField serviceName = new TextField();
        serviceName.setLabel("Service Name");
        serviceName.setRequired(true);
        TextField logoUrl = new TextField();
        logoUrl.setLabel("Logo URL");
        logoUrl.setRequired(true);
        logoUrl.setHelperText("It should be a valid URL of an image that ends with an image extension.");

        Button addServiceButton = new Button("Add");
        serviceForm.add(title, serviceName, logoUrl, addServiceButton);
        serviceForm.setAlignItems(Alignment.CENTER);

        Grid<WidgetService> servicesGrid = new Grid<WidgetService>(WidgetService.class, false);
        servicesGrid.addColumn(WidgetService::getName).setHeader("name");

        List<WidgetService> services = widgetServiceService.getAllServices();

        servicesGrid.setItems(services);

        addServiceButton.addClickListener(click -> {
            // Create a new service
            widgetServiceService.addService(new WidgetService(serviceName.getValue(), logoUrl.getValue()));

            // Get the services and display them
            List<WidgetService> newServices = widgetServiceService.getAllServices();
            servicesGrid.setItems(newServices);
        });


        // Add a widget
        H1 widgetTitle = new H1("Add a widget");

        TextField widgetName = new TextField();
        widgetName.setLabel("Widget Name");

        TextArea description = new TextArea();
        description.setLabel("Description");

        Checkbox requiresKey = new Checkbox();
        requiresKey.setLabel("Requires Key");


        NumberField reloadTime = new NumberField();
        reloadTime.setLabel("Reload Time");
        Div msSuffix = new Div();
        msSuffix.setText("ms");
        reloadTime.setSuffixComponent(msSuffix);

        TextField endpointUrl = new TextField();
        endpointUrl.setLabel("EndpointUrl");

        Select<WidgetService> servicePicker = new Select<>();
        servicePicker.setLabel("Service Provider");
        servicePicker.setRenderer(new ComponentRenderer<>(s -> {
            FlexLayout wrapper = new FlexLayout();
            wrapper.setAlignItems(Alignment.CENTER);
            Div sName = new Div();
            sName.setText(s.getName());
            wrapper.add(sName);
            return wrapper;
        }));

        servicePicker.setItems(services);

        Button addWidgetButton = new Button("Add");
        Grid<Widget> widgetsGrid = new Grid<>();
        widgetsGrid.addColumn(Widget::getName).setHeader("Name");
        widgetsGrid.addColumn(Widget::getEndpointUrl).setHeader("URL");

        List<Widget> widgets = widgetRepository.findAll();

        widgetsGrid.setItems(widgets);

        addWidgetButton.addClickListener(click -> {
            widgetRepository.save(new Widget(widgetName.getValue(), description.getValue(), requiresKey.getValue(), reloadTime.getValue(), endpointUrl.getValue(), servicePicker.getValue()));
            // Update the widgets gird
            List<Widget> newWidgets = widgetRepository.findAll();

            widgetsGrid.setItems(newWidgets);
        });

        VerticalLayout widgetForm = new VerticalLayout();
        widgetForm.setAlignItems(Alignment.CENTER);

        widgetForm.add(widgetTitle, widgetName, description, requiresKey, reloadTime, endpointUrl, servicePicker, addWidgetButton);


        add(serviceForm, servicesGrid, widgetForm, widgetsGrid);
    }
}
