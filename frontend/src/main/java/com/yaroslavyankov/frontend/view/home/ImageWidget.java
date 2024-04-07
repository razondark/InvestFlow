package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class ImageWidget extends InfoWidget {

    public ImageWidget(String title) {
        super(title);

        Div div = new Div();
        div.getStyle().set("max-width", "90%")
                .set("max-height", "90%");
        Image image = new Image("images/duck.jpg", "Sorry");

        div.add(image);

        add(div);
    }
}
