package com.yaroslavyankov.frontend.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.yaroslavyankov.frontend.view.bond.BondInfoView;
import com.yaroslavyankov.frontend.view.home.HomeView;
import com.yaroslavyankov.frontend.view.stock.StocksInfoView;

import java.util.Optional;

@PageTitle("Workspace | InvestFlow")
@Route("workspace")
@CssImport("./styles/style.css")
@Uses(Chart.class)
@Uses(Grid.class)
public class MainView extends AppLayout {

    private final Tabs menu;

    private H1 viewTitle;

    public MainView() {
        setPrimarySection(Section.DRAWER);

        addToNavbar(true, createHeaderContent());

        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        // Configure styling for the header
        layout.setId("header");
        layout.getThemeList().set("light", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H1();
        viewTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("line-height", "var(--lumo-size-l)")
                .set("margin", "var(--lumo-space-l) var(--lumo-space-l)");
        HorizontalLayout subLayout = new HorizontalLayout();
        subLayout.add(new DrawerToggle());
        subLayout.add(viewTitle);
        subLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(subLayout);

        // A logout button
        Button logoutButton = new Button("Logout", event -> {
            UI.getCurrent().navigate("/login");
        });
        logoutButton.getStyle()
                .set("margin", "var(--lumo-space-l) var(--lumo-space-l)");
        layout.add(logoutButton);

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();

        // Configure styling for the drawer
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        H1 appTitle = new H1("InvestFlow");
        appTitle.getStyle().set("font-size", "var(--lumo-font-size-xl)")
                .set("line-height", "var(--lumo-size-l)")
                .set("margin", "var(--lumo-space-l) var(--lumo-space-l)");

        // Display the logo and the menu in the drawer
        layout.add(appTitle, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab("Главная", HomeView.class, VaadinIcon.HOME.create()),
                createTab("Общая информация", GeneralInfoView.class, VaadinIcon.WALLET.create()),
                createTab("Портфель", PortfolioView.class, VaadinIcon.BRIEFCASE.create()),
                createTab("Операции", OperationView.class, VaadinIcon.BOOK.create()),
                createTab("", HomeView.class, VaadinIcon.LINE_H.create()),
                createTab("Акции", StocksInfoView.class, VaadinIcon.CASH.create()),
                createTab("Облигации", BondInfoView.class, VaadinIcon.SCALE.create())
        };
    }

    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget, Icon icon) {
        final Tab tab = new Tab(icon);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Select the tab corresponding to currently shown view
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Set the view title in the header
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
