package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PortfolioWidget extends InfoWidget {

    public PortfolioWidget(String title, PortfolioWidgetBody body) {
        super(title);
        addClassName("portfolio-widget");

        HorizontalLayout portfolioInfo = new HorizontalLayout();

        VerticalLayout priceInfo = new VerticalLayout();
        Span subtitle = new Span("Стоимость в рублях");
        subtitle.addClassName("widget-subtitle");
        subtitle.getStyle().set("font-size", "12");
        Paragraph info = new Paragraph(String.valueOf(body.getTotalAmountPortfolio()) + "\u20BD");
        info.getStyle().set("font-size", "16");
        priceInfo.add(subtitle, info);

        VerticalLayout expectedYieldInfo = new VerticalLayout();
        Span yieldSubtitle = new Span("Доходность");
        yieldSubtitle.addClassName("widget-subtitle");
        subtitle.getStyle().set("font-size", "12");
        Span yield = new Span(String.valueOf(body.getExpectedYield()));
        BigDecimal percent = body.getExpectedYield().divide(body.getTotalAmountPortfolio(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        Span percentSpan = new Span(String.valueOf(percent));
        Paragraph yieldInfo = new Paragraph(yield.getText() + "\u20BD (" + percentSpan.getText() + "%)");
        yieldInfo.getStyle().set("color", body.getExpectedYield().compareTo(BigDecimal.ZERO) >= 0  ? "green" : "red");
        info.getStyle().set("font-size", "16");
        expectedYieldInfo.add(yieldSubtitle, yieldInfo);

        portfolioInfo.add(priceInfo, expectedYieldInfo);

        add(portfolioInfo);
    }
}
