package com.yaroslavyankov.frontend.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.yaroslavyankov.frontend.service.OperationService;

@Route(value = "operations", layout = MainView.class)
@PageTitle("Операции")
public class OperationView extends VerticalLayout implements BeforeEnterObserver {

    private final OperationService operationService;


    public OperationView(OperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        // Configure layout
        setSizeFull();
        setMargin(false);
        setSpacing(false);


    }
}
