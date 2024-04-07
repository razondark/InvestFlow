package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoWidget extends Div {

    public InfoWidget(String title) {
        addClassName("info-widget");

        // Create layout for content
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setMargin(true);

        // Add title, field name, and value
        contentLayout.add(new H3(title));

        // Add content layout to the widget
        add(contentLayout);
    }
}
