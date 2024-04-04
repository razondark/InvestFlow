package com.yaroslavyankov.frontend.view.stock;

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
import com.yaroslavyankov.frontend.util.OrderTypeUtil;
import com.yaroslavyankov.frontend.service.StockService;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import com.yaroslavyankov.frontend.view.LoginView;
import com.yaroslavyankov.frontend.view.MainView;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Route(value = "stocks", layout = MainView.class)
@PageTitle("Акции")
public class StocksInfoView extends VerticalLayout implements BeforeEnterObserver {

    private final StockService stockService;

    public StocksInfoView(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        setSizeFull();
        setMargin(false);
        setSpacing(false);

        try {
            List<StockDto> stocks = stockService.getStocks(accessToken);

            Grid<StockDto> grid = new Grid<>(StockDto.class, false);
            grid.addColumn(createEmployeeRenderer()).setHeader("Акция")
                    .setAutoWidth(true).setFlexGrow(0)
                    .setSortable(true);
            grid.addColumn(p -> p.getPrice().setScale(2, RoundingMode.HALF_UP) + " " + Currency.getInstance(p.getCurrency().toUpperCase(Locale.ROOT)).getSymbol()).setHeader("Цена")
                    .setSortable(true)
                    .setAutoWidth(true);
            grid.addColumn(StockDto::getSector).setHeader("Отрасль")
                    .setAutoWidth(true)
                    .setSortable(true);
            grid.addColumn(StockDto::getLots).setHeader("Лотность")
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

    private Dialog addBuyDialog(StockDto stock) {
        Dialog dialog = new Dialog("Информация об акции");

        // dialog fields
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setWidth("500px");

        var tickerField = new TextField("Тикер");
        tickerField.setValue(stock.getTicker());
        tickerField.setReadOnly(true);
        dialogLayout.add(tickerField);

        var figiField = new TextField("Figi");
        figiField.setValue(stock.getFigi());
        figiField.setReadOnly(true);
        figiField.setWidthFull();
        dialogLayout.add(figiField);

        var nameField = new TextField("Название");
        nameField.setValue(stock.getName());
        nameField.setReadOnly(true);
        nameField.setWidthFull();
        dialogLayout.add(nameField);

        var sectorField = new TextField("Сектор");
        sectorField.setValue(stock.getSector());
        sectorField.setReadOnly(true);
        sectorField.setWidthFull();
        dialogLayout.add(sectorField);

        var currencyField = new TextField("Валюта");
        currencyField.setValue(stock.getCurrency());
        currencyField.setReadOnly(true);
        currencyField.setWidthFull();
        dialogLayout.add(currencyField);

        var countryField = new TextField("Страна");
        countryField.setValue(stock.getCountryOfRiskName());
        countryField.setReadOnly(true);
        countryField.setWidthFull();
        dialogLayout.add(countryField);

        var priceField = new TextField("Текущая цена");
        priceField.setValue(String.valueOf(stock.getPrice()));
        priceField.setReadOnly(true);
        priceField.setWidthFull();
        dialogLayout.add(priceField);

        var sumField = new TextField("Сумма");
        sumField.setReadOnly(true);
        sumField.setWidthFull();

        var lotsField = new TextField("Количество лотов");
        lotsField.setValue(String.valueOf(stock.getLots()));
        lotsField.setReadOnly(false);
        lotsField.setWidthFull();
        lotsField.addValueChangeListener(e -> {
            sumField.setValue(stock.getPrice().multiply(new BigDecimal(lotsField.getValue())).toString());
        });
        dialogLayout.add(lotsField);

        // settings sum field
        sumField.setValue(stock.getPrice().multiply(new BigDecimal(lotsField.getValue())).toString());
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
            var result = purchase(stock, lotsField.getValue(), orderType.getValue());
            if (result == null) {
                var failed = purchaseFailed();
                failed.open();
            }
            else {
                var success = purchaseSuccess();
                success.open();
            }
        });
        buyButton.setWidth("200px");

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.setWidth("100px");

        horizontalLayout.add(cancelButton);
        horizontalLayout.add(buyButton);

        dialog.add(horizontalLayout);

        return dialog;
    }

    private OrderResponse<StockDto> purchase(StockDto stock, String lots, String orderType) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        var purchaseRequest = new PurchaseRequest();
        purchaseRequest.setAccountId("a7c911bb-5b01-41bf-9db7-3767ac46385d");
        purchaseRequest.setFigi(stock.getFigi());
        purchaseRequest.setLot(Integer.valueOf(lots));
        purchaseRequest.setOrderType(OrderType.ORDER_TYPE_BESTPRICE);

        return stockService.buyStock(purchaseRequest, accessToken);
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

    private static Renderer<StockDto> createEmployeeRenderer() {
        return LitRenderer.<StockDto> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar alt=\"User avatar\" style=\"width:40px;\" name=\"${item.ticker}\"><img style=\"max-width:100%;\" src=\"${item.pictureUrl}\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.ticker} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.name}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("pictureUrl", s -> s.getBrandLogo().getLogoUrl())
                .withProperty("ticker", StockDto::getTicker)
                .withProperty("name", StockDto::getName);
    }
}
