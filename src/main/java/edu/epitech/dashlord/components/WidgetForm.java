package edu.epitech.dashlord.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;

public class WidgetForm extends Div {
    public WidgetForm() {
        TextField widgetName = new TextField();
        widgetName.setLabel("Name");
        add(widgetName);
    }

}
