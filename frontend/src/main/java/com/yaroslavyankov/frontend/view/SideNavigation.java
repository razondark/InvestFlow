package com.yaroslavyankov.frontend.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;

/**
 * @Deprecated
 */

@Route("navigation")
public class SideNavigation extends Div {

    public SideNavigation() {

        SideNav portfolio = new SideNav();
        portfolio.setLabel("Ваш счёт");
        portfolio.addItem(new SideNavItem("Общая информация", GeneralInfoView.class, VaadinIcon.WALLET.create()));
        portfolio.addItem(new SideNavItem("Портфель", "/portfolio", VaadinIcon.BRIEFCASE.create()));
        portfolio.addItem(new SideNavItem("Операции", "/operation", VaadinIcon.CHART_TIMELINE.create()));

        SideNav invest = new SideNav();
        invest.setLabel("Инвестиции");
        invest.setCollapsible(true);
        invest.addItem(new SideNavItem("Акции", "/shares", VaadinIcon.CASH.create()));
        invest.addItem(new SideNavItem("Облигации", "/bonds", VaadinIcon.SCALE.create()));


        VerticalLayout navWrapper = new VerticalLayout(portfolio, invest);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        portfolio.setWidthFull();
        invest.setWidthFull();
        add(navWrapper);


        this.addClassName("navigation");
    }

}
