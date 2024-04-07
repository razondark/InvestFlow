package com.yaroslavyankov.frontend.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.yaroslavyankov.frontend.BalanceResponse;
import com.yaroslavyankov.frontend.dto.PortfolioRequest;
import com.yaroslavyankov.frontend.dto.PortfolioResponse;
import com.yaroslavyankov.frontend.dto.PositionResponse;
import com.yaroslavyankov.frontend.props.InvestLinkProperties;
import com.yaroslavyankov.frontend.service.AccountService;
import com.yaroslavyankov.frontend.service.exception.AccessDeniedException;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Route(value = "workspace/general", layout = MainView.class)
@PageTitle("Общая информация")
public class GeneralInfoView extends VerticalLayout {
    private final RestTemplate restTemplate;
    private final InvestLinkProperties investLinkProperties;
    private AccountService accountService;

    @Autowired
    public GeneralInfoView(@Autowired RestTemplate restTemplate, @Autowired InvestLinkProperties investLinkProperties,
                           @Autowired AccountService accountService) {
        this.restTemplate = restTemplate;
        this.investLinkProperties = investLinkProperties;
        this.accountService = accountService;

        addClassName("general-info");

        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout layout1 = new HorizontalLayout();
        HorizontalLayout layout2 = new HorizontalLayout();
        HorizontalLayout layout3 = new HorizontalLayout();
        HorizontalLayout layout4 = new HorizontalLayout();

        layout.getStyle().set("padding", "20");
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setAlignItems(Alignment.BASELINE);

        layout.getElement().getStyle().set("white-space", "normal");
        layout.getElement().getStyle().set("word-wrap", "break-word");

        TextField accountIdField = new TextField("Аккаунт ID");
        accountIdField.setReadOnly(true);

        TextField expectedYieldField = new TextField("Ожидаемая доходность портфеля");
        expectedYieldField.setReadOnly(true);

        TextField totalSharesField = new TextField("Цена акций в портфеле");
        totalSharesField.setReadOnly(true);

        TextField totalBondsField = new TextField("Цена облигаций в портфеле");
        totalBondsField.setReadOnly(true);

        TextField totalCurrenciesField = new TextField("Валюта");
        totalCurrenciesField.setReadOnly(true);

        TextField totalPortfolioField = new TextField("Сумма портфеля");
        totalPortfolioField.setReadOnly(true);

        TextField totalAssetCountField = new TextField("Количество активов");
        totalAssetCountField.setReadOnly(true);

        TextField totalSharesCountField = new TextField("Количество акций");
        totalSharesCountField.setReadOnly(true);

        TextField totalBondsCountField = new TextField("Количество облигаций");
        totalBondsCountField.setReadOnly(true);

        TextField currentBalanceField = new TextField("Текущий баланс");
        currentBalanceField.setReadOnly(true);

        TextField addedBalanceField = new TextField("Сумма пополнения");
        Button addedBalanceButton = new Button("Пополнить");
        addedBalanceButton.addClickListener(e -> {
            var addBalance = addBalance("a7c911bb-5b01-41bf-9db7-3767ac46385d", new BigDecimal(addedBalanceField.getValue()), accessToken);
            currentBalanceField.setValue(addBalance.getBalance().setScale(2, RoundingMode.HALF_UP).toString());

            //if (addBalance != null) {
                var dialog = addBalanceDialogResult("a7c911bb-5b01-41bf-9db7-3767ac46385d",
                                                        addBalance.getAddedMoney(),
                                                        addBalance.getBalance());

                dialog.open();
            //}
        });

        PortfolioResponse portfolio;
        try {
            portfolio = getPortfolio("a7c911bb-5b01-41bf-9db7-3767ac46385d", accessToken);
            int sharesCount = getSharesCount(portfolio);
            int bondCount = getBondsCount(portfolio);

            accountIdField.setValue(portfolio.getAccountId());
            expectedYieldField.setValue(String.valueOf(portfolio.getExpectedYield().setScale(2, RoundingMode.HALF_UP) + " Р"));
            totalSharesField.setValue(String.valueOf(portfolio.getTotalAmountShares().setScale(2, RoundingMode.HALF_UP)));
            totalBondsField.setValue(String.valueOf(portfolio.getTotalAmountBonds().setScale(2, RoundingMode.HALF_UP)));
            totalCurrenciesField.setValue(String.valueOf(portfolio.getTotalAmountCurrencies().setScale(2, RoundingMode.HALF_UP)));
            totalPortfolioField.setValue(String.valueOf(portfolio.getTotalAmountPortfolio().setScale(2, RoundingMode.HALF_UP)));
            totalAssetCountField.setValue(String.valueOf(portfolio.getPositions().size()));
            totalSharesCountField.setValue(String.valueOf(sharesCount));
            totalBondsCountField.setValue(String.valueOf(bondCount));

            currentBalanceField.setValue(String.valueOf(getBalance("a7c911bb-5b01-41bf-9db7-3767ac46385d", accessToken)
                    .getBalance().setScale(2, RoundingMode.HALF_UP)) + " Р");

            layout1.add(accountIdField, expectedYieldField, totalSharesField);
            layout2.add(totalBondsField, totalCurrenciesField, totalPortfolioField);
            layout3.add(totalAssetCountField, totalSharesCountField, totalBondsCountField);
            layout4.add(currentBalanceField, addedBalanceField, addedBalanceButton);

            layout.add(layout1, layout2, layout3, layout4);

            add(layout);
        } catch (AccessDeniedException e) {
            UI.getCurrent().navigate("/login");
        } catch (GettingProtfolioException e) {
            Notification.show(e.getMessage());
        }
    }

    private Dialog addBalanceDialogResult(String accountId, BigDecimal addedBalance, BigDecimal balance) {
        Dialog dialog = new Dialog("Информация о пополнении баланса");

        Text textPayIn = new Text("Сумма пополнения: " + addedBalance + "; Баланс: " + balance.setScale(2, RoundingMode.HALF_UP));
        dialog.add(textPayIn);

        return dialog;
    }

    private int getSharesCount(PortfolioResponse portfolio) {
        int sharesAmount = 0;
        for (PositionResponse position : portfolio.getPositions()) {
            if (position.getInstrumentType().equals("share")) {
                sharesAmount += position.getQuantity();
            }
        }
        return sharesAmount;
    }

    private int getBondsCount(PortfolioResponse portfolio) {
        int bondsAmount = 0;
        for (PositionResponse position : portfolio.getPositions()) {
            if (position.getInstrumentType().equals("bond")) {
                bondsAmount += position.getQuantity();
            }
        }
        return bondsAmount;
    }

    private BalanceResponse getBalance(String accountId, String accessToken) {
        return accountService.getBalance(accountId, accessToken);
    }

    private BalanceResponse addBalance(String accountId, BigDecimal addedMoney, String accessToken) {
        return accountService.addBalance(accountId, addedMoney, accessToken);
    }

    private PortfolioResponse getPortfolio(String accountId, String accessToken) {

        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setAccountId(accountId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(portfolioRequest, headers);

        ResponseEntity<PortfolioResponse> response = null;
        try {
            response = restTemplate
                    .exchange(investLinkProperties.getPortfolioLink(), HttpMethod.POST, request, PortfolioResponse.class);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new AccessDeniedException("Сессия истекла.");
            }
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new AccessDeniedException("Сессия истекла.");
        } else {
            throw new GettingProtfolioException("Не удалось получить портфель инвестиций.");
        }
    }
}
