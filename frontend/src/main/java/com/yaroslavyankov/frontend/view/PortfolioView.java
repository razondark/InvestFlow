package com.yaroslavyankov.frontend.view;

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
import com.yaroslavyankov.frontend.service.OperationService;
import com.yaroslavyankov.frontend.service.PortfolioService;
import com.yaroslavyankov.frontend.service.StockService;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import com.yaroslavyankov.frontend.util.OrderTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Route(value = "portfolio", layout = MainView.class)
@PageTitle("Портфель")
public class PortfolioView extends VerticalLayout implements BeforeEnterObserver {

    private final PortfolioService portfolioService;

    private final BondService bondService;

    private final StockService stockService;

    public PortfolioView(@Autowired PortfolioService portfolioService,
                         @Autowired BondService bondService,
                         @Autowired StockService stockService) {
        this.portfolioService = portfolioService;
        this.bondService = bondService;
        this.stockService = stockService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        // Configure layout
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        removeAll();

        try {
            PortfolioResponse portfolio = portfolioService.getPortfolio("a7c911bb-5b01-41bf-9db7-3767ac46385d", accessToken);

            List<PositionResponse> positions = portfolio.getPositions();

            List<StockDto> shares = getShares(positions);
            for (int i = 0; i < shares.size(); i++) {
                if (shares.get(i).getPrice() == null) { // check price
                    shares.get(i).setPrice(BigDecimal.ZERO);
                }

                // TODO: change
                // change lots to Quantity
                shares.get(i).setLots(positions.get(i).getQuantity());
            }

            List<BondDto> bonds = getBonds(positions);
            for (int i = 0; i < bonds.size(); i++) {
                if (bonds.get(i).getPrice() == null) { // check price
                    bonds.get(i).setPrice(BigDecimal.ZERO);
                }

                // TODO: change
                // change lots to Quantity
                bonds.get(i).setLots(positions.get(i).getQuantity());
            }

            // grid stock
            Grid<StockDto> gridStock = new Grid<>(StockDto.class, false);
            gridStock.addColumn(createEmployeeRendererStock()).setHeader("Акция")
                    .setAutoWidth(true).setFlexGrow(0)
                    .setSortable(true);
            gridStock.addColumn(p -> p.getPrice().setScale(2, RoundingMode.HALF_UP) + " " + Currency.getInstance(p.getCurrency().toUpperCase(Locale.ROOT)).getSymbol())
                    .setHeader("Текущая цена")
                    .setSortable(true)
                    .setAutoWidth(true);
            gridStock.addColumn(StockDto::getSector).setHeader("Отрасль")
                    .setAutoWidth(true)
                    .setSortable(true);
            gridStock.addColumn(StockDto::getLots).setHeader("Количество")
                    .setAutoWidth(true);

            gridStock.setItems(shares);
            gridStock.addItemDoubleClickListener(e -> {
                var dialog = addStockDialog(e.getItem(), getStockDtoLots(e.getItem(), accessToken));
                dialog.open();
            });

            add(gridStock);

            // grid bond
            Grid<BondDto> gridBond = new Grid<>(BondDto.class, false);
            gridBond.addColumn(createEmployeeRendererBond()).setHeader("Облигация")
                    .setAutoWidth(true).setFlexGrow(0)
                    .setSortable(true);
            gridBond.addColumn(p -> p.getPrice().setScale(2, RoundingMode.HALF_UP) + " " + Currency.getInstance(p.getCurrency().toUpperCase(Locale.ROOT)).getSymbol())
                    .setHeader("Текущая цена")
                    .setSortable(true)
                    .setAutoWidth(true);
            gridBond.addColumn(BondDto::getSector).setHeader("Отрасль")
                    .setAutoWidth(true)
                    .setSortable(true);
            gridBond.addColumn(BondDto::getLots).setHeader("Количество")
                    .setAutoWidth(true);

            gridBond.setItems(bonds);
            gridBond.addItemDoubleClickListener(e -> {
                var dialog = addBondDialog(e.getItem(), getBondDtoLots(e.getItem(), accessToken));
                dialog.open();
            });

            add(gridBond);

        } catch (HttpClientErrorException.Unauthorized e) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("/login");
        } catch (GettingProtfolioException e) {
            H2 exceptionTitle = new H2("Не удалось получить портфель по счёту");
            exceptionTitle.getStyle()
                    .set("margin", "0 auto");
        }
    }

    private Integer getStockDtoLots(StockDto stock, String token) {
        var stockInfo = stockService.getStockByFigi(stock.getFigi(), token);
        if (stockInfo != null) {
            return stockInfo.get(0).getLots();
        }

        return stock.getLots();
    }

    private Integer getBondDtoLots(BondDto bond, String token) {
        var bondInfo = bondService.getBondByFigi(bond.getFigi(), token);
        if (bondInfo != null) {
            return bondInfo.get(0).getLots();
        }

        return bond.getLots();
    }

    private Dialog addStockDialog(StockDto stock, Integer lots) {
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
        lotsField.setValue(String.valueOf(lots));
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

        Button saleButton = new Button("Продать");
        saleButton.addClickListener(e -> {
            dialog.close();
            var result = sale(stock, lotsField.getValue(), orderType.getValue());
            if (result == null) {
                var failed = saleFailed();
                failed.open();
            }
            else {
                var success = saleSuccess();
                success.open();
            }
        });
        saleButton.setWidth("150px");

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.setWidth("100px");

        horizontalLayout.add(cancelButton);
        horizontalLayout.add(saleButton);
        horizontalLayout.add(buyButton);

        dialog.add(horizontalLayout);

        return dialog;
    }

    private OrderResponse<StockDto> sale(StockDto stock, String lots, String orderType) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        var saleRequest = new SaleRequest();
        saleRequest.setAccountId("a7c911bb-5b01-41bf-9db7-3767ac46385d");
        saleRequest.setFigi(stock.getFigi());
        saleRequest.setLot(Integer.valueOf(lots));
        saleRequest.setOrderType(OrderType.ORDER_TYPE_BESTPRICE); // TODO: change and other too

        return stockService.saleStock(saleRequest, accessToken);
    }

    private OrderResponse<BondDto> sale(BondDto bond, String lots, String orderType) {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("accessToken");

        var saleRequest = new SaleRequest();
        saleRequest.setAccountId("a7c911bb-5b01-41bf-9db7-3767ac46385d");
        saleRequest.setFigi(bond.getFigi());
        saleRequest.setLot(Integer.valueOf(lots));
        saleRequest.setOrderType(OrderType.ORDER_TYPE_BESTPRICE); // TODO: change and other too

        return bondService.saleBond(saleRequest, accessToken);
    }

    private Dialog saleFailed() {
        // TODO: change
        Dialog dialog = new Dialog("Информация о продаже");

        Text text = new Text("Ошибка продажи");
        dialog.add(text);

        return dialog;
    }

    private Dialog saleSuccess() {
        // TODO: change
        Dialog dialog = new Dialog("Информация о продаже");

        Text text = new Text("Заявка на продажу успешно выставлена");
        dialog.add(text);

        return dialog;
    }

    private Dialog addBondDialog(BondDto bond, Integer lots) {
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
        lotsField.setValue(String.valueOf(lots));
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
        buyButton.setWidth("200px");

        Button saleButton = new Button("Продать");
        saleButton.addClickListener(e -> {
            dialog.close();
            var result = sale(bond, lotsField.getValue(), orderType.getValue());
            if (result == null) {
                var failed = saleFailed();
                failed.open();
            }
            else {
                var success = saleSuccess();
                success.open();
            }
        });
        saleButton.setWidth("150px");

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.setWidth("100px");

        horizontalLayout.add(cancelButton);
        horizontalLayout.add(saleButton);
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

        Text text = new Text("Заявка на покупку успешно создана");
        dialog.add(text);

        return dialog;
    }

    private List<StockDto> getShares(List<PositionResponse> positions) {
        List<StockDto> shares = new ArrayList<>();

        for (PositionResponse position : positions) {
            if (position.getInstrumentType().equals("share")) {
                StockDto stockDto = (StockDto) position.getAsset();
                shares.add(stockDto);
            }
        }

        return shares;
    }

    private List<BondDto> getBonds(List<PositionResponse> positions) {
        List<BondDto> bonds = new ArrayList<>();

        for (PositionResponse position : positions) {
            if (position.getInstrumentType().equals("bond")) {
                BondDto bondDto = (BondDto) position.getAsset();
                bonds.add(bondDto);
            }
        }

        return bonds;
    }

    private static Renderer<StockDto> createEmployeeRendererStock() {
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

    private static Renderer<BondDto> createEmployeeRendererBond() {
        return LitRenderer.<BondDto> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar alt=\"User avatar\" style=\"width:40px;\" name=\"${item.ticker}\"><img style=\"max-width:100%;\" src=\"${item.pictureUrl}\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.ticker} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.name}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("pictureUrl", s -> s.getBrandLogo().getLogoUrl())
                .withProperty("ticker", BondDto::getTicker)
                .withProperty("name", BondDto::getName);
    }
}
