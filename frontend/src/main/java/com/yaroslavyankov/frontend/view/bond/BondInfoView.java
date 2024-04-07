package com.yaroslavyankov.frontend.view.bond;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.yaroslavyankov.frontend.dto.*;
import com.yaroslavyankov.frontend.service.BondService;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import com.yaroslavyankov.frontend.util.OrderTypeUtil;
import com.yaroslavyankov.frontend.view.LoginView;
import com.yaroslavyankov.frontend.view.MainView;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Route(value = "bonds", layout = MainView.class)
@PageTitle("Акции")
public class BondInfoView extends VerticalLayout implements BeforeEnterObserver {

    private final BondService bondService;

    public BondInfoView(BondService bondService) {
        this.bondService = bondService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        setSizeFull();
        setMargin(false);
        setSpacing(false);

        try {
            List<BondDto> stocks = bondService.getBonds(accessToken);

            Grid<BondDto> grid = new Grid<>(BondDto.class, false);
            grid.addColumn(createEmployeeRenderer()).setHeader("Облигация")
                    .setAutoWidth(true).setFlexGrow(0)
                    .setSortable(true);
            grid.addColumn(p -> p.getPrice().setScale(2, RoundingMode.HALF_UP) + " " + Currency.getInstance(p.getCurrency().toUpperCase(Locale.ROOT)).getSymbol()).setHeader("Цена")
                    .setSortable(true)
                    .setAutoWidth(true);
            grid.addColumn(BondDto::getSector).setHeader("Отрасль")
                    .setAutoWidth(true)
                    .setSortable(true);
            grid.addColumn(BondDto::getLots).setHeader("Лотность")
                    .setAutoWidth(true);

            grid.setItems(stocks);

            grid.addItemDoubleClickListener(e -> {
                var dialog = addBuyDialog(e.getItem());
                dialog.open();
            });

            add(grid);
        } catch (HttpClientErrorException.Unauthorized e) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("/login");
        } catch (GettingProtfolioException e) {
            H2 exceptionTitle = new H2("Не удалось получить портфель по счёту");
            exceptionTitle.getStyle()
                    .set("margin", "0 auto");
        }
    }

    private Dialog addBuyDialog(BondDto bond) {
        Dialog dialog = new Dialog("Информация об акции");

        // dialog fields
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setWidth("500px");

        var tickerField = new TextField("Тикер");
        tickerField.setValue(bond.getTicker());
        tickerField.setReadOnly(true);
        dialogLayout.add(tickerField);

        var figiField = new TextField("Figi");
        figiField.setValue(bond.getFigi());
        figiField.setReadOnly(true);
        figiField.setWidthFull();
        dialogLayout.add(figiField);

        var nameField = new TextField("Название");
        nameField.setValue(bond.getName());
        nameField.setReadOnly(true);
        nameField.setWidthFull();
        dialogLayout.add(nameField);

        var sectorField = new TextField("Сектор");
        sectorField.setValue(bond.getSector());
        sectorField.setReadOnly(true);
        sectorField.setWidthFull();
        dialogLayout.add(sectorField);

        var currencyField = new TextField("Валюта");
        currencyField.setValue(bond.getCurrency());
        currencyField.setReadOnly(true);
        currencyField.setWidthFull();
        dialogLayout.add(currencyField);

        var couponQuantityPerYearField = new TextField("Количество купонов в год");
        couponQuantityPerYearField.setValue(String.valueOf(bond.getCouponQuantityPerYear()));
        couponQuantityPerYearField.setReadOnly(true);
        couponQuantityPerYearField.setWidthFull();
        dialogLayout.add(couponQuantityPerYearField);

        var maturityDateField = new TextField("Дата погашения");
        maturityDateField.setValue(bond.getMaturityDate().toString());
        maturityDateField.setReadOnly(true);
        maturityDateField.setWidthFull();
        dialogLayout.add(maturityDateField);

        var countryField = new TextField("Страна");
        countryField.setValue(bond.getCountryOfRiskName());
        countryField.setReadOnly(true);
        countryField.setWidthFull();
        dialogLayout.add(countryField);

        var placementPriceField = new TextField("Цена размещения");
        placementPriceField.setValue(String.valueOf(bond.getPlacementPrice()));
        placementPriceField.setReadOnly(true);
        placementPriceField.setWidthFull();
        dialogLayout.add(placementPriceField);

        var priceField = new TextField("Текущая цена");
        priceField.setValue(String.valueOf(bond.getPrice()));
        priceField.setReadOnly(true);
        priceField.setWidthFull();
        dialogLayout.add(priceField);

        var sumField = new TextField("Сумма");
        sumField.setReadOnly(true);
        sumField.setWidthFull();

        var lotsField = new TextField("Количество лотов");
        lotsField.setValue(String.valueOf(bond.getLots()));
        lotsField.setReadOnly(false);
        lotsField.setWidthFull();
        lotsField.addValueChangeListener(e -> {
            sumField.setValue(bond.getPrice().multiply(new BigDecimal(lotsField.getValue())).toString());
        });
        dialogLayout.add(lotsField);

        // settings sum field
        sumField.setValue(bond.getPrice().multiply(new BigDecimal(lotsField.getValue())).toString());
        dialogLayout.add(sumField);

        var orderType = new ComboBox<String>("Тип заявки");
        orderType.setItems(OrderTypeUtil.getRussianValues());
        orderType.setValue(OrderTypeUtil.ORDER_TYPE_LIMIT.getRussianValue());
        orderType.setWidthFull();
        dialogLayout.add(orderType);

        // add dialog layout
        dialog.add(dialogLayout);

        // buttons
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("500px");

        Button buyButton = new Button("Купить");
        buyButton.addClickListener(e -> {
            dialog.close();
            var result = purchase(bond, lotsField.getValue(), orderType.getValue());
            if (result == null) {
                var failed = purchaseFailed();
                failed.open();
            }
            else {
                var success = purchaseSuccess();
                success.open();
            }
        });
        buyButton.setWidth("400px");

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.setWidth("100px");

        horizontalLayout.add(cancelButton);
        horizontalLayout.add(buyButton);

        dialog.add(horizontalLayout);

        return dialog;
    }

    private OrderResponse<BondDto> purchase(BondDto bond, String lots, String orderType) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        var purchaseRequest = new PurchaseRequest();
        purchaseRequest.setAccountId("a7c911bb-5b01-41bf-9db7-3767ac46385d");
        purchaseRequest.setFigi(bond.getFigi());
        purchaseRequest.setLot(Integer.valueOf(lots));
        purchaseRequest.setOrderType(OrderType.ORDER_TYPE_BESTPRICE);

        return bondService.buyBond(purchaseRequest, accessToken);
    }

    private Dialog purchaseFailed() {
        // TODO: change
        Dialog dialog = new Dialog("Информация о покупке");

        Text text = new Text("Ошибка покупки");
        dialog.add(text);

        return dialog;
    }

    private Dialog purchaseSuccess() {
        // TODO: change
        Dialog dialog = new Dialog("Информация о покупке");

        Text text = new Text("Покупка успешно совершена");
        dialog.add(text);

        return dialog;
    }

    private static Renderer<BondDto> createEmployeeRenderer() {
        return LitRenderer.<BondDto>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar alt=\"User avatar\" style=\"width:40px;\" name=\"${item.ticker}\"><img style=\"max-width:100%;\" src=\"${item.pictureUrl}\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.ticker} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.name}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("pictureUrl", b -> b.getBrandLogo().getLogoUrl())
                .withProperty("ticker", BondDto::getTicker)
                .withProperty("name", BondDto::getName);
    }

}
