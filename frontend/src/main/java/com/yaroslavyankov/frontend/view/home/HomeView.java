package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.yaroslavyankov.frontend.dto.Operation;
import com.yaroslavyankov.frontend.dto.OperationResponse;
import com.yaroslavyankov.frontend.dto.PortfolioResponse;
import com.yaroslavyankov.frontend.dto.PositionResponse;
import com.yaroslavyankov.frontend.service.OperationService;
import com.yaroslavyankov.frontend.service.PortfolioService;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import com.yaroslavyankov.frontend.view.GridLayout;
import com.yaroslavyankov.frontend.view.LoginView;
import com.yaroslavyankov.frontend.view.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Route(value = "home", layout = MainView.class)
@PageTitle("Главная")
@RouteAlias(value = "/workspace/home", layout = MainView.class)
public class HomeView extends VerticalLayout implements BeforeEnterObserver {
    private final PortfolioService portfolioService;

    private final OperationService operationService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        // Configure layout
        setSizeFull();
        setMargin(false);
        setSpacing(false);

        try {
            PortfolioResponse portfolio = portfolioService.getPortfolio("a7c911bb-5b01-41bf-9db7-3767ac46385d", accessToken);
            OperationResponse operationResponse = operationService.getOperations("a7c911bb-5b01-41bf-9db7-3767ac46385d", accessToken);



            PortfolioWidget portfolioWidget = getPortfolioWidget(portfolio);
            AnalyticsWidget analyticsWidget = getSectorChartWidget(portfolio);
            OperationWidget operationWidget = getOperationWidget(operationResponse);
            ImageWidget imageWidget = new ImageWidget("...");

            InfoWidget infoWidget = new InfoWidget("In progress...");
            // Configure grid layout
            GridLayout gridLayout = new GridLayout();
            gridLayout.setSizeFull();

            gridLayout.add(portfolioWidget);
            gridLayout.add(operationWidget);
            gridLayout.add(analyticsWidget);
            gridLayout.add(infoWidget);

            // Add grid layout to the view
            add(gridLayout);

        } catch (HttpClientErrorException.Unauthorized e) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("/login");
        } catch (GettingProtfolioException e) {
            H2 exceptionTitle = new H2("Не удалось получить портфель по счёту");
            exceptionTitle.getStyle()
                    .set("margin", "0 auto");
        }
    }

    public HomeView(@Autowired PortfolioService portfolioService, @Autowired OperationService operationService) {
        this.portfolioService = portfolioService;
        this.operationService = operationService;
    }

    private PortfolioWidget getPortfolioWidget(PortfolioResponse portfolio) {
        BigDecimal expectedYield = portfolio.getExpectedYield().setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmountPortfolio = portfolio.getTotalAmountPortfolio().setScale(1, RoundingMode.HALF_UP);
        int positionCount = portfolio.getPositions().size();

        PortfolioWidgetBody body = new PortfolioWidgetBody();
        body.setTotalAmountPortfolio(totalAmountPortfolio);
        body.setExpectedYield(expectedYield);
        body.setPositionCount(positionCount);

        return new PortfolioWidget("Ваш счёт", body);
    }

    private OperationWidget getOperationWidget(OperationResponse operationResponse) {
        List<Operation> operations = operationResponse.getOperations();

        return new OperationWidget("Операции", operations);
    }

    private AnalyticsWidget getSectorChartWidget(PortfolioResponse portfolioResponse) {
        List<PositionResponse> positions = portfolioResponse.getPositions();

        return new AnalyticsWidget("Аналитика", positions);
    }


}
